package lyeoj.tfcthings.init;

import lyeoj.tfcthings.main.TFCThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TFCThingsSoundEvents {
    public static final SoundEvent WHETSTONE_SHARPEN = new SoundEvent(new ResourceLocation(TFCThings.MODID, "whetstone.sharpen")).setRegistryName("sharpen");

    public static final SoundEvent[] SOUND_EVENTS = {
            WHETSTONE_SHARPEN
    };
}
