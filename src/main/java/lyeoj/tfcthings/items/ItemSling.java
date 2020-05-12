package lyeoj.tfcthings.items;

import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemSling extends Item implements IItemSize {

    public ItemSling() {
        this.setTranslationKey("sling");
        this.setRegistryName("sling");
        this.setMaxDamage(100);
        this.setNoRepair();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabsTFC.CT_MISC);
        this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter()
            {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
                {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
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

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo(playerIn).isEmpty();

        if (!playerIn.isCreative() && !flag) {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;

            boolean flag = entityplayer.isCreative();
            ItemStack itemStack = this.findAmmo(entityplayer);

            int power = Math.min((this.getMaxItemUseDuration(stack) - timeLeft) / 8, 10);
            float velocity = 1.6F * (power / 10.0F);
            float inaccuracy = 0.5F * (10.0F - power);
            //System.out.println(power);

            if(!itemStack.isEmpty() && !flag) {
                if (!worldIn.isRemote) {
                    EntitySlingStone entitySlingStone = new EntitySlingStone(worldIn, entityLiving, power);
                    entitySlingStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, velocity, inaccuracy);
                    worldIn.spawnEntity(entitySlingStone);
                }
                worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                itemStack.shrink(1);
                if(itemStack.isEmpty()) {
                    entityplayer.inventory.deleteStack(itemStack);
                }
                stack.damageItem(1, entityplayer);

            } else if(flag) {
                if (!worldIn.isRemote) {
                    EntitySlingStone entitySlingStone = new EntitySlingStone(worldIn, entityLiving, power);
                    entitySlingStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, velocity, inaccuracy);
                    worldIn.spawnEntity(entitySlingStone);
                }
                worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    private ItemStack findAmmo(EntityPlayer player) {
        if (this.isStone(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isStone(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isStone(itemstack)) {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    protected boolean isStone(ItemStack stack) {
        return stack.getItem() instanceof ItemRock;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("The bigger they are...");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
