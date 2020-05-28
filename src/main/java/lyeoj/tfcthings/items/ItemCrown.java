package lyeoj.tfcthings.items;

import net.dries007.tfc.api.capability.damage.IDamageResistance;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemCrown extends ItemArmor implements IItemSize, IDamageResistance {

    private final String gem;

    public ItemCrown(int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String metal, String gem) {
        super(EnumHelper.addArmorMaterial("crown_" + metal + "_" + gem, "tfcthings:crown/" + metal + "_" + gem, 10, new int[]{0, 0, 0, 0}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F), renderIndexIn, equipmentSlotIn);
        this.gem = gem;
        this.setTranslationKey(metal + "_crown");
        this.setRegistryName("crown/" + metal + "_" + gem.toLowerCase());
        this.setNoRepair();
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.HEAVY;
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public float getCrushingModifier() {
        return 0;
    }

    @Override
    public float getPiercingModifier() {
        return 0;
    }

    @Override
    public float getSlashingModifier() {
        return 0;
    }

    public boolean isDamageable() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Gem: " + gem);
        if(gem.equals("Empty")) {
            tooltip.add("Something seems missing from this...");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
