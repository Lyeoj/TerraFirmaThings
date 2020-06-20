package lyeoj.tfcthings.main;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(
        modid = TFCThings.MODID
)
public class ConfigTFCThings {

    public ConfigTFCThings() {}

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TFCThings.MODID)) {
            TFCThings.LOGGER.warn("Config changed");
            ConfigManager.sync(TFCThings.MODID, Config.Type.INSTANCE);
        }
    }

    @Config(
            modid = TFCThings.MODID,
            category = "items",
            name = "TerraFirmaThings - Items"
    )
    @Config.LangKey("config.tfcthings.items")
    public static final class Items {

        @Config.Comment({"Sling Settings"})
        @Config.LangKey("config.tfcthings.items.sling")
        public static final SlingCFG SLING = new SlingCFG();
        @Config.Comment({"Bear Trap Settings"})
        @Config.LangKey("config.tfcthings.items.beartrap")
        public static final BearTrapCFG BEAR_TRAP = new BearTrapCFG();
        @Config.Comment({"Whetstone Settings"})
        @Config.LangKey("config.tfcthings.items.whetstone")
        public static final WhetstoneCFG WHETSTONE = new WhetstoneCFG();

        public static final class SlingCFG {
            @Config.Comment({"Damage multiplier against predator animals.", "Predator damage = sling damage * multiplier"})
            @Config.RangeDouble (
                    min = 1.0D,
                    max = 10.0D
            )
            @Config.LangKey("config.tfcthings.items.predatorMultiplier")
            public double predatorMultiplier = 2.0D;

            @Config.Comment({"The maximum power a sling can be charged up to.", "A fully charged sling will deal damage equal to maximum power, but projectile speed is fixed to the ratio: current power / max power."})
            @Config.RangeInt (
                    min = 1
            )
            @Config.LangKey("config.tfcthings.items.maxPower")
            public int maxPower = 8;

            @Config.Comment({"The speed at which the sling charges.", "Value represents number of ticks per power level (lower = faster)."})
            @Config.RangeInt (
                    min = 1
            )
            @Config.LangKey("config.tfcthings.items.chargeSpeed")
            public int chargeSpeed = 16;
        }

        public static final class BearTrapCFG {
            @Config.Comment("Percent chance for a bear trap to break when harvested after being activated (a predator breakout will attempt to break the trap with double this chance).")
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.items.breakChance")
            public double breakChance = 0.1D;

            @Config.Comment({"The chance a predator has to break out of a bear trap each tick.", "0 = no breakouts. If this number isn't kept very small then breakouts will happen very fast. 1 = instant breakout."})
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.items.breakoutChance")
            public double breakoutChance = 0.001D;

            @Config.Comment({"The duration of the debuffs applied by the bear trap in ticks.", "Set to 0 to disable the debuffs."})
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.debuffDuration")
            public int debuffDuration = 1000;

            @Config.Comment({"The fraction of an entity's health that is dealt as damage when stepping in a trap.", "E.g. 3 = 1/3 current health dealt as damage. Less than 1 will deal more damage than current health, probably an instakill. Set to 0 to do no damage."})
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 20.0D
            )
            @Config.LangKey("config.tfcthings.items.healthCut")
            public double healthCut = 3.0D;
        }

        public static final class WhetstoneCFG {
            @Config.Comment({"List of items that can be sharpened by a whetstone.", "You must provide the registry name for each item you want to add."})
            @Config.LangKey("config.tfcthings.items.canSharpen")
            public String[] canSharpen = new String[0];

            @Config.Comment("The additional mining speed added to a sharpened tool.")
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.bonusSpeed")
            public int bonusSpeed = 4;

            @Config.Comment("The amount of extra damage a weapon does when sharpened. This damage ignores armor.")
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.damageBoost")
            public int damageBoost = 2;

        }

    }

    @Config(
            modid = TFCThings.MODID,
            category = "misc",
            name = "TerraFirmaThings - Misc"
    )
    @Config.LangKey("config.tfcthings.misc")
    public static final class Misc {

        @Config.Comment({"Add Special Days!"})
        @Config.LangKey("config.tfcthings.misc.birthdays")
        public static final BirthdayCFG BIRTHDAYS = new BirthdayCFG();
        @Config.Comment({"Pigvil Settings"})
        @Config.LangKey("config.tfcthings.misc.pigvil")
        public static final PigvilCFG PIGVIL = new PigvilCFG();

        public static final class BirthdayCFG {
            @Config.Comment({"Add special days to the TFC calendar!", "Format: MONTH<dayNumber> <name of your day>.", "An invalid day string won't be read by the calendar. See defaults for examples."})
            @Config.LangKey("config.tfcthings.misc.daylist")
            @Config.RequiresMcRestart
            public String[] dayList = new String[]{"APRIL2 MeteorFreak's Birthday", "APRIL10 Pakratt0013's Birthday"};
        }

        public static final class PigvilCFG {
            @Config.Comment("The percent chance to create a Pigvil when feeding a pig iron carrot to a male pig")
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.misc.convertChance")
            public double convertChance = 0.25D;
        }

    }

    public static void addBirthday(String birthday) {
        if(birthday != null) {
            String[] text = birthday.split(" ");
            String day = text[0];
            String name = "";
            for(int i = 1; i < text.length; i++) {
                name += " " + text[i];
            }
            if(CalendarTFC.BIRTHDAYS.get(day) == null) {
                CalendarTFC.BIRTHDAYS.put(day, name);
            }
        }
    }

}