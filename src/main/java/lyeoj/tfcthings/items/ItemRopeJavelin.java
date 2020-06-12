package lyeoj.tfcthings.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.objects.items.ItemQuiver;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemRopeJavelin extends ItemTFC implements IMetalItem {

    private final Metal metal;
    public final ToolMaterial material;
    private final double attackDamage;
    private final float attackSpeed;

    public ItemRopeJavelin(Metal metal, String name) {
        this.metal = metal;
        this.material = metal.getToolMetal();
        setCreativeTab(CreativeTabsTFC.CT_METAL);
        this.setMaxDamage((int)((double)material.getMaxUses() * 0.4D));
        this.attackDamage = (double)(0.7 * this.material.getAttackDamage());
        this.attackSpeed = -1.8F;
        this.setTranslationKey("rope_javelin_" + name);
        this.setRegistryName("rope_javelin/" + name);
        //OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
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

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }

    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
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
                    EntityThrownJavelin javelin = new EntityThrownJavelin(worldIn, player);
                    javelin.setDamage(this.attackDamage);
                    javelin.setWeapon(stack);
                    javelin.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 1.5F, 0.5F);
                    worldIn.spawnEntity(javelin);
                    worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, TFCSounds.ITEM_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (Constants.RNG.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                }

                if (!((EntityPlayer)entityLiving).isCreative()) {
                    player.inventory.deleteStack(stack);
                }
            }
        }

    }

}
