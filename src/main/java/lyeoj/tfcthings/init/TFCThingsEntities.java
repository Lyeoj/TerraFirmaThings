package lyeoj.tfcthings.init;

import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.renderer.RenderSlingStone;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class TFCThingsEntities {

    public static final NonMobEntityInfo[] NON_MOB_ENTITY_INFOS = {
            new NonMobEntityInfo("slingstone", EntitySlingStone.class, 0, 64, 2, true, RenderSlingStone.FACTORY)
    };

    public static class NonMobEntityInfo {
        public String name;
        @SuppressWarnings("rawtypes")
        public Class entityClass;
        public int id;
        public int trackingRange;
        public int updateFrequency;
        public boolean sendsVelocityUpdates;
        public IRenderFactory factory;

        @SuppressWarnings("rawtypes")
        public NonMobEntityInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, IRenderFactory factory) {
            this.name = name;
            this.entityClass = entityClass;
            this.id = id;
            this.trackingRange = trackingRange;
            this.updateFrequency = updateFrequency;
            this.sendsVelocityUpdates = sendsVelocityUpdates;
            this.factory = factory;
        }
    }
}
