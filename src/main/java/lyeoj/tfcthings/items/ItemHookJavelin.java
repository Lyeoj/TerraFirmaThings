package lyeoj.tfcthings.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lyeoj.tfcthings.entity.projectile.EntityThrownHookJavelin;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemHookJavelin extends ItemRopeJavelin {

    public ItemHookJavelin(Metal metal, String name) {
        super(metal, name);
        this.setMaxDamage((int)((double)material.getMaxUses() * 0.3D));
        this.pullStrength = 0.2;
    }

    protected EntityThrownRopeJavelin makeNewJavelin(World worldIn, EntityPlayer player) {
        return new EntityThrownHookJavelin(worldIn, player);
    }

    protected String getNamePrefix() {
        return "hook_javelin";
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(!playerIn.onGround) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if(isThrown(itemstack) && getCapturedEntity(itemstack, worldIn) == null) {
                if(getJavelin(itemstack, worldIn) != null) {
                    EntityThrownHookJavelin javelin = (EntityThrownHookJavelin)getJavelin(itemstack, worldIn);
                    javelin.setLength(javelin.getLength() - 0.5f);
                    if(playerIn.isSneaking()) {
                        this.retractJavelin(itemstack, worldIn);
                    }
                }
                return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            } else {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
        } else {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND && !isThrown(stack)) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage * 0.4, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

}
