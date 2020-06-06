package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.TFCThingsCapabilityHandler;
import lyeoj.tfcthings.init.TFCThingsEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new TFCThingsCapabilityHandler());
		CapabilitySharpness.setup();
		TFCThingsEntities.registerEntities();
	}
	public void init(FMLInitializationEvent event) {}

	public void postInit(FMLPostInitializationEvent event) {}
	public void serverStarting(FMLServerStartingEvent event) {}
	public void serverStopping(FMLServerStoppingEvent event) {}

}
