package lyeoj.tfcthings.entity.projectile;

import lyeoj.tfcthings.items.ItemHookJavelin;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.network.MessageHookJavelinUpdate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityThrownHookJavelin extends EntityThrownRopeJavelin {

    private static final DataParameter<Float> LENGTH = EntityDataManager.<Float>createKey(EntityThrownHookJavelin.class, DataSerializers.FLOAT);
    private static final String LENGTH_NBT_KEY = "length";
    private boolean inGroundSynced = false;

    public EntityThrownHookJavelin(World world) {
        super(world);
    }

    public EntityThrownHookJavelin(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityThrownHookJavelin(World world, EntityLivingBase shooter) {
        super(world, shooter);
        this.effectiveRange = 4096;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(LENGTH, 0.0f);
    }

    public void setLength(float length) {
        if(length < 1.0f) {
            this.getDataManager().set(LENGTH, 1.0f);
        } else if(length > 60) {
            this.getDataManager().set(LENGTH, 60.0f);
        } else {
            this.getDataManager().set(LENGTH, length);
        }

    }

    public float getLength() {
        return this.getDataManager().get(LENGTH);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setFloat(LENGTH_NBT_KEY, getLength());
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        if(compound.hasKey(LENGTH_NBT_KEY, 99)) {
            setLength(compound.getFloat(LENGTH_NBT_KEY));
        }
        super.readEntityFromNBT(compound);
    }

    public void setInGroundSynced(boolean inGroundSynced) {
        this.inGroundSynced = inGroundSynced;
    }

    protected void performAdditionalUpdates() {
        if(getWeapon().getItem() instanceof ItemHookJavelin && getThrower() != null) {
            EntityLivingBase thrower = (EntityLivingBase) this.getThrower();
            float length = getLength();
            if(!world.isRemote && (inGroundSynced != inGround)) {
                inGroundSynced = inGround;
                TFCThings.network.sendTo(new MessageHookJavelinUpdate(getEntityId(), inGroundSynced), (EntityPlayerMP)thrower);
            }
            if(thrower != null && (inGround || inGroundSynced) && this.posY > thrower.posY && getDistance(thrower) > length && !thrower.onGround) {
                thrower.fallDistance = 0;
                Vec3d rope = thrower.getPositionVector().subtract(this.getPositionVector()).normalize();
                Vec3d velocity = new Vec3d(thrower.motionX, thrower.motionY, thrower.motionZ);
                Vec3d motion = velocity.normalize().subtract(rope);
                double speed = velocity.length();
                thrower.motionX = motion.x * speed;
                if(Math.abs(thrower.motionX) > 1.4) {
                    thrower.motionX = thrower.motionX > 0 ? 1.4 : -1.4;
                }
                thrower.motionY = motion.y * speed;
                if(thrower.motionY > 1.0) {
                    thrower.motionY = 1.0;
                }
                thrower.motionZ = motion.z * speed;
                if(Math.abs(thrower.motionZ) > 1.4) {
                    thrower.motionZ = thrower.motionZ > 0 ? 1.4 : -1.4;
                }
                if(speed < 0.09 && getDistance(thrower) > length + 0.3) {
                    thrower.motionY = 0.1;
                }
            } else if(thrower != null && thrower.onGround && getDistance(thrower) > length) {
                setLength(getDistance(thrower));
            }
            if(thrower != null && thrower.isSneaking()) {
                setLength(getLength() + 0.2f);
            }
        }

    }

    protected void onHit(@Nonnull RayTraceResult raytraceResultIn) {
        super.onHit(raytraceResultIn);
        if(getWeapon().getItem() instanceof ItemHookJavelin) {
            EntityLivingBase thrower = (EntityLivingBase)this.getThrower();
            setLength(getDistance(thrower));
        }
    }

}
