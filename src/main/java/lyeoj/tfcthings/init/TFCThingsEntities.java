package lyeoj.tfcthings.init;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.entity.projectile.EntitySlingStone;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.entity.projectile.EntityUnknownProjectile;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.renderer.RenderPigvil;
import lyeoj.tfcthings.renderer.RenderSlingStone;
import lyeoj.tfcthings.renderer.RenderUnknownProjectile;
import net.dries007.tfc.client.render.projectile.RenderThrownJavelin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TFCThingsEntities {

    public static final MobInfo[] MOB_ENTITY_INFOS = {
            new MobInfo("pigvil", EntityPigvil.class, 1, 64, 3, true, 0XF1AAAA, 0X555555, RenderPigvil::new)
    };

    public static final NonMobEntityInfo[] NON_MOB_ENTITY_INFOS = {
            new NonMobEntityInfo("slingstone", EntitySlingStone.class, 0, 64, 2, true, RenderSlingStone::new),
            new NonMobEntityInfo("unknownprojectile", EntityUnknownProjectile.class, 2, 64, 2, true, RenderUnknownProjectile::new),
            new NonMobEntityInfo("ropejavelinthrown", EntityThrownRopeJavelin.class, 3, 64, 2, true, RenderThrownJavelin::new)
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
        IRenderFactory factory;

        public MobInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggP, int eggS, IRenderFactory factory) {
            this.name = name;
            this.entityClass = entityClass;
            this.id = id;
            this.trackingRange = trackingRange;
            this.updateFrequency = updateFrequency;
            this.sendsVelocityUpdates = sendsVelocityUpdates;
            this.eggP = eggP;
            this.eggS = eggS;
            this.factory = factory;
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
        IRenderFactory factory;

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

    public static void registerEntities() {
        for(MobInfo info : MOB_ENTITY_INFOS) {
            EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
                    info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
                    info.updateFrequency, info.sendsVelocityUpdates, info.eggP, info.eggS);
            RenderingRegistry.registerEntityRenderingHandler(info.entityClass, info.factory);
        }
        for(NonMobEntityInfo info : NON_MOB_ENTITY_INFOS) {
            EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
                    info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
                    info.updateFrequency, info.sendsVelocityUpdates);
            RenderingRegistry.registerEntityRenderingHandler(info.entityClass, info.factory);
        }
    }

}
