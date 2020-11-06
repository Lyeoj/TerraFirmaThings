package lyeoj.tfcthings.items;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.ISharpness;
import lyeoj.tfcthings.event.TFCThingsEventHandler;
import lyeoj.tfcthings.init.TFCThingsSoundEvents;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemWhetstone extends Item implements IItemSize, IMetalItem, ItemOreDict, TFCThingsConfigurableItem {


    private int tier;

    public ItemWhetstone(int tier, int durability) {
        setCreativeTab(CreativeTabs.MISC);
        setMaxDamage(durability);
        this.tier = tier;
        setNoRepair();
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 1000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(handIn.equals(EnumHand.MAIN_HAND)) {
            if(playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
                playerIn.setActiveHand(handIn);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
        }
        return new ActionResult(EnumActionResult.FAIL, itemstack);
    }


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer playerIn = (EntityPlayer)entityLiving;
            if(timeLeft < 985 && playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
                ItemStack item = playerIn.getHeldItemOffhand();
                ISharpness capability = TFCThingsEventHandler.getSharpnessCapability(item);
                if(capability != null && capability.getCharges() < getMaxCharges()) {
                    for(int i = 0; i < tier; i++) {
                        if(capability.getCharges() >= getMaxCharges())
                            break;
                        capability.addCharge();
                    }
                    if(Math.random() < 0.8) {
                        item.damageItem(1, entityLiving);
                    }
                    stack.damageItem(1, entityLiving);
                    playerIn.playSound(TFCThingsSoundEvents.WHETSTONE_SHARPEN, 1.0f, 1.0f);
                } else {
                    if(!worldIn.isRemote) {
                        playerIn.sendMessage(new TextComponentTranslation("tfcthings.tooltip.maximum_sharpness", new Object[0]));
                    }
                }
            }
        }
    }

    private int getMaxCharges() {
        switch(tier) {
            case 2:
                return 256;
            case 3:
                return 384;
            default:
                return 64;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tfcthings.tooltip.whetstone", new Object[0]));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nullable
    @Override
    public Metal getMetal(ItemStack itemStack) {
        return tier > 1 ? TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL) : null;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        if(tier > 1) {
            if (this.isDamageable() && itemStack.isItemDamaged()) {
                double d = (double)(itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double)itemStack.getMaxDamage() - 0.1D;
                return d < 0.0D ? 0 : MathHelper.floor((double)200 * d);
            } else {
                return 200;
            }
        }
        return 0;
    }

    @Override
    public boolean canMelt(ItemStack stack) {
        return tier > 1 ? true : false;
    }

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return tier > 1 ? new ForgeableHeatableHandler(nbt, 0.35F, 1540.0F) : null;
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre("tool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones;
    }
}
