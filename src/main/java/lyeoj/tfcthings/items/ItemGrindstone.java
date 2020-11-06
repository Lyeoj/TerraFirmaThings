package lyeoj.tfcthings.items;

import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.forge.ForgeableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemGrindstone extends ItemTFC implements TFCThingsConfigurableItem {

    private final int tier;

    public ItemGrindstone(int tier, int durability, String name) {
        setCreativeTab(CreativeTabs.MISC);
        setMaxDamage(durability);
        this.tier = tier;
        setNoRepair();
        setMaxStackSize(1);
        setTranslationKey(name);
        setRegistryName(name);
    }

    public int getTier() {
        return tier;
    }

    public int getMaxCharges() {
        switch(tier) {
            case 2:
                return 256;
            case 3:
                return 384;
            default:
                return 64;
        }
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


    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(tier == 1) {
            return new ForgeableHandler(nbt);
        } else {
            return null;
        }
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGrindstones;
    }
}
