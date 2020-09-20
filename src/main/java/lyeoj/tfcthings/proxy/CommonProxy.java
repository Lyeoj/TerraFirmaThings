package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.TFCThingsCapabilityHandler;
import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new TFCThingsCapabilityHandler());
		CapabilitySharpness.setup();
		TFCThingsEntities.registerEntities();
		CalendarTFC.BIRTHDAYS.put("OCTOBER4", "Lyeoj's Birthday");
		for(int i = 0; i < ConfigTFCThings.Misc.BIRTHDAYS.dayList.length; i++) {
			ConfigTFCThings.addBirthday(ConfigTFCThings.Misc.BIRTHDAYS.dayList[i]);
		}
	}
	public void init(FMLInitializationEvent event) {}

	public void postInit(FMLPostInitializationEvent event) {}
	public void serverStarting(FMLServerStartingEvent event) {}
	public void serverStopping(FMLServerStoppingEvent event) {}

	public IThreadListener getThreadListener(final MessageContext context) {
		if(context.side.isClient()) {
			throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
		} else {
			return context.getServerHandler().player.server;
		}
	}

	public void syncJavelinGroundState(int javelinID, boolean inGround) {
		throw new WrongSideException("Tried to sync hook javelin on the server");
	}

	class WrongSideException extends RuntimeException {
		public WrongSideException(final String message) {
			super(message);
		}

		public WrongSideException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}

}
