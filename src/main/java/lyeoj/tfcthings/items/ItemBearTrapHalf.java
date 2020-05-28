package lyeoj.tfcthings.items;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemBearTrapHalf extends ItemTFC {

    public ItemBearTrapHalf() {
        setTranslationKey("bear_trap_half");
        setRegistryName("bear_trap_half");
        setCreativeTab(CreativeTabsTFC.CT_METAL);
    }

    @Nonnull
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.NORMAL;
    }

    @Nonnull
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.HEAVY;
    }

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ItemHeatHandler(nbt, 0.35F, 1540.0F);
    }

}
