package lyeoj.tfcthings.items;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.ISharpness;
import lyeoj.tfcthings.init.TFCThingsSoundEvents;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemWhetstone extends Item implements IItemSize {


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
            if(timeLeft < 980 && playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
                ItemStack item = playerIn.getHeldItemOffhand();
                ISharpness capability = item.getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
                if(capability.getCharges() < getMaxCharges()) {
                    for(int i = 0; i < tier; i++) {
                        if(capability.getCharges() >= getMaxCharges())
                            break;
                        capability.addCharge();
                    }
                    item.damageItem(1, entityLiving);
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
            default:
                return 64;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Use with a metal tool in your off-hand");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
