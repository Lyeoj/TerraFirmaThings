package lyeoj.tfcthings.items;

import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.entity.projectile.EntitySlingStoneMetal;
import lyeoj.tfcthings.entity.projectile.EntitySlingStoneMetalLight;
import lyeoj.tfcthings.entity.projectile.EntityUnknownProjectile;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemSling extends Item implements IItemSize, ItemOreDict, TFCThingsConfigurableItem {

    private int tier;

    public ItemSling(int tier) {
        this.tier = tier;
        if(tier < 1) {
            this.setMaxDamage(64);
        } else {
            this.setMaxDamage(256);
        }
        this.setNoRepair();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabsTFC.CT_MISC);
        this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter()
            {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if(entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && entityIn.getItemInUseMaxCount() > 0) {
                    int maxPower = ConfigTFCThings.Items.SLING.maxPower;
                    int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;
                    float powerRatio = Math.min((float)entityIn.getItemInUseMaxCount() / (float)chargeSpeed, maxPower) / (float)maxPower;
                    float f = MathHelper.floor(((entityIn.getItemInUseMaxCount() * powerRatio) % 8) + 1);
                    return f;
                }
                return 0.0F;
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

            int maxPower = ConfigTFCThings.Items.SLING.maxPower;
            int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;

            int power = Math.min((this.getMaxItemUseDuration(stack) - timeLeft) / chargeSpeed, maxPower);
            float velocity = 1.6F * (power / (float)maxPower);
            float inaccuracy = 0.5F * (8.0F - power);

            if(!itemStack.isEmpty() && !flag) {

                if (!worldIn.isRemote) {
                    shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
                }
                worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                itemStack.shrink(1);
                if(itemStack.isEmpty()) {
                    entityplayer.inventory.deleteStack(itemStack);
                }
                stack.damageItem(1, entityplayer);

            } else if(flag) {
                if (!worldIn.isRemote) {
                    shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
                }
                worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    private void shoot(World worldIn, EntityLivingBase entityLiving, int power, float velocity, float inaccuracy, ItemStack itemStack) {

        EntitySlingStone entitySlingStone;
        float adjustedVelocity = velocity;

        if(itemStack.getItem() instanceof ItemIngot) {
            entitySlingStone = new EntityUnknownProjectile(worldIn, entityLiving, power);
        } else if(itemStack.getItem() instanceof ItemMetalSlingAmmo) {
            ItemMetalSlingAmmo ammo = (ItemMetalSlingAmmo)itemStack.getItem();
            switch(ammo.getType()) {
                case 0:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 5);
                    break;
                case 1:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
                    for(int i = 0; i < 4; i++) {
                        EntitySlingStoneMetal bonusStone = new EntitySlingStoneMetal(worldIn, entityLiving, power);
                        bonusStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, velocity * 0.75f, inaccuracy + 2.5f);
                        worldIn.spawnEntity(bonusStone);
                    }
                    break;
                case 2:
                    entitySlingStone = new EntitySlingStoneMetalLight(worldIn, entityLiving, power + 3);
                    adjustedVelocity *= 1.2;
                    break;
                default:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
                    entitySlingStone.setFire(10);
            }
        } else {
            entitySlingStone = new EntitySlingStone(worldIn, entityLiving, power);
        }
        entitySlingStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, adjustedVelocity, inaccuracy);
        worldIn.spawnEntity(entitySlingStone);
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
        if(stack.getItem() instanceof ItemRock) {
            return true;
        } else if(stack.getItem() instanceof ItemIngot) {
            ItemIngot ingot = (ItemIngot)stack.getItem();
            if(ingot.getMetal(stack) == Metal.UNKNOWN) {
                return true;
            }
        } else if(tier > 0 && stack.getItem() instanceof ItemMetalSlingAmmo) {
            return true;
        }
        return false;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(tier < 1) {
            tooltip.add(I18n.format("tfcthings.tooltip.sling.message1", new Object[0]));
        } else {
            tooltip.add(I18n.format("tfcthings.tooltip.sling.message2", new Object[0]));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre("tool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));;
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSling;
    }
}
