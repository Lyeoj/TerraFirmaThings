package lyeoj.tfcthings.items;

import lyeoj.tfcthings.entity.projectile.EntityRopeBridgeThrown;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemRopeBridge extends ItemTFC implements TFCThingsConfigurableItem {

    public ItemRopeBridge() {
        this.setRegistryName("rope_bridge_bundle");
        this.setTranslationKey("rope_bridge_bundle");
        this.setCreativeTab(CreativeTabsTFC.CT_MISC);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.LIGHT;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int charge = this.getMaxItemUseDuration(stack) - timeLeft;
        if (charge > 5) {
            float f = ItemBow.getArrowVelocity(charge);
            if(!worldIn.isRemote) {
                EntityRopeBridgeThrown bridge = new EntityRopeBridgeThrown(worldIn, entityLiving, stack);
                bridge.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, f * 1.5f, 0.0f);
                worldIn.spawnEntity(bridge);
            }
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeBridge;
    }

    @Override
    public int getStackSize(@Nonnull ItemStack stack) {
        return ConfigTFCThings.Items.ROPE_BRIDGE.maxStackSize;
    }
}
