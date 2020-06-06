package lyeoj.tfcthings.items;

import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemHoningSteelHead extends ItemTFC {

    public ItemHoningSteelHead() {
        setTranslationKey("honing_steel_head");
        setRegistryName("honing_steel_head");
        setCreativeTab(CreativeTabsTFC.CT_METAL);
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

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ForgeableHeatableHandler(nbt, 0.35F, 1540.0F);
    }

}
