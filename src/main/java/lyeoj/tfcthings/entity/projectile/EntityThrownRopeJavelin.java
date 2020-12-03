package lyeoj.tfcthings.entity.projectile;

import io.netty.buffer.ByteBuf;
import lyeoj.tfcthings.items.ItemRopeJavelin;
import net.dries007.tfc.Constants;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

import javax.annotation.Nonnull;

public class EntityThrownRopeJavelin extends EntityArrow implements IThrowableEntity, IEntityAdditionalSpawnData {
    private ItemStack weapon;
    private int knockbackStrength;
    protected double effectiveRange = 1024;

    public EntityThrownRopeJavelin(World world) {
        super(world);
        this.weapon = ItemStack.EMPTY;
        this.knockbackStrength = 0;
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityThrownRopeJavelin(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.weapon = ItemStack.EMPTY;
        this.knockbackStrength = 0;
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityThrownRopeJavelin(World world, EntityLivingBase shooter) {
        super(world, shooter);
        this.weapon = ItemStack.EMPTY;
        this.knockbackStrength = 0;
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public Entity getThrower() {
        return this.shootingEntity;
    }

    public void setThrower(Entity entity) {
        this.shootingEntity = entity;
    }

    public void writeSpawnData(ByteBuf buffer) {
        ByteBufUtils.writeItemStack(buffer, this.weapon);
    }

    public void readSpawnData(ByteBuf additionalData) {
        this.setWeapon(ByteBufUtils.readItemStack(additionalData));
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }

    public void setWeapon(ItemStack stack) {
        this.weapon = stack;
    }

    public void onUpdate() {
        if(getWeapon().getItem() instanceof ItemRopeJavelin) {
            ItemRopeJavelin javelin = (ItemRopeJavelin) getWeapon().getItem();
            if(getThrower() == null) {
                javelin.retractJavelin(getWeapon(), world);
            }
            if(javelin.getCapturedEntity(getWeapon(), getEntityWorld()) != null && this.getDistanceSq(getThrower()) <= effectiveRange) {
                this.setNoGravity(true);
                Entity caughtEntity = javelin.getCapturedEntity(getWeapon(), getEntityWorld());
                this.posX = caughtEntity.posX;
                double d2 = (double)caughtEntity.height;
                this.posY = caughtEntity.getEntityBoundingBox().minY + d2 * 0.8D;
                this.posZ = caughtEntity.posZ;
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                return;
            } else {
                this.setNoGravity(false);
                this.performAdditionalUpdates();
            }
        }
        if(this.shouldRetract(getWeapon(), this.world)) {
            this.onEntityUpdate();
            return;
        }

        super.onUpdate();
    }

    protected void performAdditionalUpdates() {
    }

    protected void onHit(@Nonnull RayTraceResult raytraceResultIn) {
        if(getWeapon().getItem() instanceof ItemRopeJavelin) {
            ItemRopeJavelin javelin = (ItemRopeJavelin)getWeapon().getItem();

            Entity entity = raytraceResultIn.entityHit;
            if (this.getThrower() instanceof EntityLivingBase && javelin.getCapturedEntity(getWeapon(), getEntityWorld()) == null) {
                EntityLivingBase thrower = (EntityLivingBase)this.getThrower();
                float skillModifier = SmithingSkill.getSkillBonus(getWeapon(), SmithingSkill.Type.WEAPONS) / 2.0F;
                int damageAmount = 1;
                if (skillModifier > 0.0F && Constants.RNG.nextFloat() < skillModifier) {
                    damageAmount -= 1;
                }
                if (entity != null) {
                    damageAmount += 1;
                }

                this.weapon.damageItem(damageAmount, thrower);
            }

            if (entity != null) {
                ItemStack weapon = this.getWeapon();
                float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                float finalDamage = f * (float)this.getDamage();
                if (this.getIsCritical()) {
                    finalDamage *= 2.0F;
                }

                DamageSource damagesource;
                if (this.shootingEntity == null) {
                    damagesource = DamageSource.causeArrowDamage(this, this);
                } else {
                    damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                }

                if (this.isBurning() && !(entity instanceof EntityEnderman)) {
                    entity.setFire(5);
                }

                if(javelin.getCapturedEntity(getWeapon(), getEntityWorld()) == null) {
                    if (entity.attackEntityFrom(damagesource, finalDamage)) {
                        javelin.setCapturedEntity(getWeapon(), entity);
                        if (entity instanceof EntityLivingBase) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                            if (this.knockbackStrength > 0) {
                                float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (f1 > 0.0F) {
                                    entitylivingbase.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
                                }
                            }

                            this.arrowHit(entitylivingbase);
                            if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                            }
                        }

                        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    } else {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D) {
                            this.setDead();
                        }
                    }
                }
            } else {
                if(javelin.getCapturedEntity(getWeapon(), getEntityWorld()) == null) {
                    super.onHit(raytraceResultIn);
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        NBTTagList tag = new NBTTagList();
        tag.appendTag(this.weapon.serializeNBT());
        compound.setTag("weapon", tag);
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        NBTTagList tag = compound.getTagList("weapon", 10);
        this.weapon = tag.tagCount() > 0 ? new ItemStack(tag.getCompoundTagAt(0)) : ItemStack.EMPTY;
        super.readEntityFromNBT(compound);
    }

    @Nonnull
    protected ItemStack getArrowStack() {
        return this.weapon;
    }

    public void setKnockbackStrength(int knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }

    private boolean shouldRetract(ItemStack stack, World world)
    {
        if(stack != null && stack.getItem() instanceof ItemRopeJavelin) {
            ItemRopeJavelin javelin = (ItemRopeJavelin)getWeapon().getItem();
            if(this.getThrower() instanceof EntityLivingBase) {
                EntityLivingBase thrower = (EntityLivingBase)getThrower();
                ItemStack itemstack = thrower.getHeldItemMainhand();
                ItemStack itemstack1 = thrower.getHeldItemOffhand();
                boolean flag = ItemStack.areItemStacksEqual(itemstack, getWeapon());
                boolean flag1 = ItemStack.areItemStacksEqual(itemstack1, getWeapon());

                if (itemstack.getItem() instanceof ItemRopeJavelin) {
                    flag1 = false;
                }

                if (thrower.isEntityAlive() && (flag || flag1) && this.getDistanceSq(thrower) <= effectiveRange) {
                    return false;
                }
                else
                {
                    javelin.retractJavelin(stack, world);
                    return true;
                }
            } else {
                javelin.retractJavelin(stack, world);
                return true;
            }
        }
        if(!world.isRemote) {
            this.setDead();
        }
        return true;
    }

}
