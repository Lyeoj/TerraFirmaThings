package lyeoj.tfcthings.init;

import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.renderer.RenderSlingStone;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TFCThingsEntities {

    public static final NonMobEntityInfo[] NON_MOB_ENTITY_INFOS = {
            new NonMobEntityInfo("slingstone", EntitySlingStone.class, 0, 64, 2, true)
    };

    public static class NonMobEntityInfo {
        public String name;
        @SuppressWarnings("rawtypes")
        public Class entityClass;
        public int id;
        public int trackingRange;
        public int updateFrequency;
        public boolean sendsVelocityUpdates;

        @SuppressWarnings("rawtypes")
        public NonMobEntityInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
            this.name = name;
            this.entityClass = entityClass;
            this.id = id;
            this.trackingRange = trackingRange;
            this.updateFrequency = updateFrequency;
            this.sendsVelocityUpdates = sendsVelocityUpdates;
        }
    }

    public static void registerEntities() {
        for(NonMobEntityInfo info : NON_MOB_ENTITY_INFOS) {
            EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
                    info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
                    info.updateFrequency, info.sendsVelocityUpdates);
        }
    }

    public static void registerEntityModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySlingStone.class, RenderSlingStone.FACTORY);
    }

}
