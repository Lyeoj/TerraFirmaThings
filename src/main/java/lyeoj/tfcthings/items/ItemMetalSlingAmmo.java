package lyeoj.tfcthings.items;

import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemMetalSlingAmmo extends ItemTFC implements TFCThingsConfigurableItem {

    private final int type;

    public ItemMetalSlingAmmo(int type) {
        this.type = type;
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSling;
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

    public int getType() {
        return type;
    }

}
