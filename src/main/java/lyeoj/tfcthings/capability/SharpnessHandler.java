package lyeoj.tfcthings.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SharpnessHandler implements ISharpness {

    protected ItemStack container;
    public static final String SHARPNESS_NBT_KEY = "Sharpness";

    public SharpnessHandler(ItemStack container) {
        this.container = container;
    }

    public SharpnessHandler() {
        this.container = null;
    }

    public ItemStack getContainer() {
        return container;
    }

    public int getCharges() {
        NBTTagCompound tag = container.getTagCompound();
        if(tag != null && tag.hasKey(SHARPNESS_NBT_KEY)) {
            return tag.getInteger(SHARPNESS_NBT_KEY);
        }
        return 0;
    }

    public void setCharges(int charges) {
        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        container.getTagCompound().setInteger(SHARPNESS_NBT_KEY, charges);
    }

    public void addCharge() {
        setCharges(getCharges() + 1);
    }

    public void removeCharge() {
        if(getCharges() > 0) {
            setCharges(getCharges() - 1);
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilitySharpness.SHARPNESS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilitySharpness.SHARPNESS_CAPABILITY ? (T)this : null;
    }
}
