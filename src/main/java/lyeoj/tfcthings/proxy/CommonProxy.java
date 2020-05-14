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

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		TFCThingsEntities.registerEntities();
	}
	public void init(FMLInitializationEvent event) {}
	public void postInit(FMLPostInitializationEvent event) {}
	public void serverStarting(FMLServerStartingEvent event) {}
	public void serverStopping(FMLServerStoppingEvent event) {}

}
