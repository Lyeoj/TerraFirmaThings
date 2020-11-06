package lyeoj.tfcthings.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class ItemRopeJavelin extends ItemTFC implements IMetalItem, ItemOreDict, TFCThingsConfigurableItem {

    private final Metal metal;
    public final ToolMaterial material;
    protected final double attackDamage;
    protected final float attackSpeed;
    protected double pullStrength = 0.1;

    private static final String THROWN_NBT_KEY = "Thrown";
    private static final String JAVELIN_NBT_KEY = "JavelinID";
    private static final String CAPTURED_NBT_KEY = "CapturedID";

    public ItemRopeJavelin(Metal metal, String name) {
        this.metal = metal;
        this.material = metal.getToolMetal();
        setCreativeTab(CreativeTabsTFC.CT_METAL);
        this.setMaxDamage((int)((double)material.getMaxUses() * 0.1D));
        this.attackDamage = (double)(0.7 * this.material.getAttackDamage());
        this.attackSpeed = -1.8F;
        this.setTranslationKey(getNamePrefix() + "_" + name);
        this.setRegistryName(getNamePrefix() + "/" + name);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("thrown"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null) {
                    return 0.0F;
                }
                else {

                    boolean flag = entityIn.getHeldItemMainhand() == stack;
                    boolean flag1 = entityIn.getHeldItemOffhand() == stack;

                    if (entityIn.getHeldItemMainhand().getItem() instanceof ItemRopeJavelin) {
                        flag1 = false;
                    }
                    return (flag || flag1) && entityIn instanceof EntityPlayer && isThrown(stack) ? 1.0F : 0.0F;
                }
            }
        });
    }

    protected String getNamePrefix() {
        return "rope_javelin";
    }

    @Nullable
    @Override
    public Metal getMetal(ItemStack itemStack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        return 100;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.NORMAL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    public boolean canStack(ItemStack itemStack) {
        return false;
    }


    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND && !isThrown(stack)) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(isThrown(itemstack)) {
            EntityThrownRopeJavelin javelin = getJavelin(itemstack, worldIn);
            if(javelin != null) {
                if(getCapturedEntity(itemstack, worldIn) != null) {
                    Entity entity = getCapturedEntity(itemstack, worldIn);
                    if(entity.isRiding()) {
                        entity.dismountRidingEntity();
                    }
                    double d0 = playerIn.posX - javelin.posX;
                    double d1 = playerIn.posY - javelin.posY;
                    double d2 = playerIn.posZ - javelin.posZ;
                    entity.motionX += d0 * pullStrength;
                    entity.motionY += d1 * pullStrength;
                    entity.motionZ += d2 * pullStrength;
                }
            }
            retractJavelin(itemstack, worldIn);
        } else {
            playerIn.setActiveHand(handIn);
        }
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }

    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
        return isThrown(stack) ? EnumAction.NONE : EnumAction.BOW;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            int charge = this.getMaxItemUseDuration(stack) - timeLeft;
            if (charge > 5) {
                float f = ItemBow.getArrowVelocity(charge);
                if (!worldIn.isRemote) {
                    setThrown(stack, true);
                    EntityThrownRopeJavelin javelin = makeNewJavelin(worldIn, player);
                    javelin.setDamage(this.attackDamage);
                    javelin.setWeapon(stack);
                    javelin.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 1.5F, 0.5F);
                    setJavelin(stack, javelin);
                    getJavelin(stack, worldIn);
                    worldIn.spawnEntity(javelin);
                    worldIn.playSound(null, player.posX, player.posY, player.posZ, TFCSounds.ITEM_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (Constants.RNG.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                }
            }
        }

    }

    protected EntityThrownRopeJavelin makeNewJavelin(World worldIn, EntityPlayer player) {
        return new EntityThrownRopeJavelin(worldIn, player);
    }

    public boolean isThrown(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if(!stack.getTagCompound().hasKey(THROWN_NBT_KEY)) {
            return false;
        }
        return stack.getTagCompound().getBoolean(THROWN_NBT_KEY);
    }

    public void setThrown(ItemStack stack, boolean thrown) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setBoolean(THROWN_NBT_KEY, thrown);
    }

    public EntityThrownRopeJavelin getJavelin(ItemStack stack, World world) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        UUID javelinID = stack.getTagCompound().getUniqueId(JAVELIN_NBT_KEY);
        if(javelinID != null && world instanceof WorldServer) {
            Entity entity = ((WorldServer)world).getEntityFromUuid(javelinID);
            if(entity instanceof EntityThrownRopeJavelin) {
                return (EntityThrownRopeJavelin)entity;
            }
        }
        return null;
    }

    public void setJavelin(ItemStack stack, EntityThrownRopeJavelin javelin) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if(javelin != null) {
            stack.getTagCompound().setUniqueId(JAVELIN_NBT_KEY, javelin.getUniqueID());
        } else {
            stack.getTagCompound().setUniqueId(JAVELIN_NBT_KEY, UUID.randomUUID());
        }
    }

    public Entity getCapturedEntity(ItemStack stack, World world) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        UUID capturedID = stack.getTagCompound().getUniqueId(CAPTURED_NBT_KEY);
        if(capturedID != null && world instanceof WorldServer) {
            return ((WorldServer)world).getEntityFromUuid(capturedID);

        }
        return null;
    }

    public void setCapturedEntity(ItemStack stack, Entity entity) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if(entity != null) {
            stack.getTagCompound().setUniqueId(CAPTURED_NBT_KEY, entity.getUniqueID());
        } else {
            stack.getTagCompound().setUniqueId(CAPTURED_NBT_KEY, UUID.randomUUID());
        }
    }

    public void retractJavelin(ItemStack stack, World world) {
        setThrown(stack, false);
        setCapturedEntity(stack, null);
        if(getJavelin(stack, world) != null) {
            getJavelin(stack, world).setDead();
            setJavelin(stack, null);
        }
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre("tool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeJavelins;
    }
}
