package lyeoj.tfcthings.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilitySharpness {

    @CapabilityInject(ISharpness.class)
    public static Capability<ISharpness> SHARPNESS_CAPABILITY;

    public static void setup() {
        CapabilityManager.INSTANCE.register(ISharpness.class, new CapabilitySharpnessStorage(), SharpnessHandler::new);
    }

    private static class CapabilitySharpnessStorage implements Capability.IStorage<ISharpness> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ISharpness> capability, ISharpness instance, EnumFacing side) {
            return new NBTTagInt(instance.getCharges());
        }

        @Override
        public void readNBT(Capability<ISharpness> capability, ISharpness instance, EnumFacing side, NBTBase nbt) {
            instance.setCharges(((NBTPrimitive)nbt).getInt());
        }
    }

}
