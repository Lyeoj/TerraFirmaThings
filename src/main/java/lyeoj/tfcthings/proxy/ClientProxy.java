package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.main.TFCThings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ClientProxy extends CommonProxy {

	@SuppressWarnings("unchecked")
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		for(TFCThingsEntities.NonMobEntityInfo info : TFCThingsEntities.NON_MOB_ENTITY_INFOS) {
			EntityRegistry.registerModEntity(new ResourceLocation(TFCThings.MODID, info.name),
					info.entityClass, info.name, info.id, TFCThings.instance, info.trackingRange,
					info.updateFrequency, info.sendsVelocityUpdates);
			RenderingRegistry.registerEntityRenderingHandler(info.entityClass, info.factory);
		}
	}
	
	@Override
	public void init(FMLInitializationEvent event) {		
		super.init(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
	}
	
	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		super.serverStopping(event);
	}
	
}
