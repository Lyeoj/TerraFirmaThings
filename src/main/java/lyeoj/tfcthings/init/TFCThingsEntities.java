package lyeoj.tfcthings.init;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.renderer.RenderPigvil;
import lyeoj.tfcthings.renderer.RenderSlingStone;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TFCThingsEntities {

    public static final MobInfo[] MOB_ENTITY_INFOS = {
            new MobInfo("pigvil", EntityPigvil.class, 1, 64, 3, true, 0XF1AAAA, 0X555555)
    };

    public static final NonMobEntityInfo[] NON_MOB_ENTITY_INFOS = {
            new NonMobEntityInfo("slingstone", EntitySlingStone.class, 0, 64, 2, true)
    };

    public static class MobInfo {
        public String name;
        public Class entityClass;
        public int id;
        public int trackingRange;
        public int updateFrequency;
        public boolean sendsVelocityUpdates;
        public int eggP;
        public int eggS;

        public MobInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggP, int eggS) {
            this.name = name;
            this.entityClass = entityClass;
            this.id = id;
            this.trackingRange = trackingRange;
            this.updateFrequency = updateFrequency;
            this.sendsVelocityUpdates = sendsVelocityUpdates;
            this.eggP = eggP;
            this.eggS = eggS;
        }
    }

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
        for(MobInfo info : MOB_ENTITY_INFOS) {
            EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
                    info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
                    info.updateFrequency, info.sendsVelocityUpdates, info.eggP, info.eggS);
        }
        for(NonMobEntityInfo info : NON_MOB_ENTITY_INFOS) {
            EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
                    info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
                    info.updateFrequency, info.sendsVelocityUpdates);
        }
    }

    public static void registerEntityModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySlingStone.class, RenderSlingStone.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigvil.class, RenderPigvil.FACTORY);
    }

}
