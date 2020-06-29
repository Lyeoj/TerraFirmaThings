package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.TFCThingsCapabilityHandler;
import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

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

}
