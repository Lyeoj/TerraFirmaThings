package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.entity.projectile.EntityThrownHookJavelin;
import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.renderer.TESRGemDisplay;
import lyeoj.tfcthings.renderer.TESRGrindstone;
import lyeoj.tfcthings.tileentity.TileEntityGemDisplay;
import lyeoj.tfcthings.tileentity.TileEntityGrindstone;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	private final Minecraft MINECRAFT = Minecraft.getMinecraft();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		TFCThingsEntities.registerEntityModels();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {		
		super.init(event);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGemDisplay.class, new TESRGemDisplay());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrindstone.class, new TESRGrindstone());
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

	public IThreadListener getThreadListener(final MessageContext context) {
		if(context.side.isClient()) {
			return MINECRAFT;
		} else {
			return context.getServerHandler().player.server;
		}
	}

	public void syncJavelinGroundState(int javelinID, boolean inGround) {
		EntityThrownHookJavelin javelin = (EntityThrownHookJavelin)MINECRAFT.world.getEntityByID(javelinID);
		javelin.setInGroundSynced(inGround);
	}
	
}
