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
        @Config.Comment({"Snare Settings"})
        @Config.LangKey("config.tfcthings.items.snare")
        public static final SnareCFG SNARE = new SnareCFG();
        @Config.Comment({"Whetstone Settings"})
        @Config.LangKey("config.tfcthings.items.whetstone")
        public static final WhetstoneCFG WHETSTONE = new WhetstoneCFG();
        @Config.Comment({"Snow Shoes Settings"})
        @Config.LangKey("config.tfcthings.items.snow_shoes")
        public static final SnowShoesCFG SNOW_SHOES = new SnowShoesCFG();
        @Config.Comment({"Hiking Boots Settings"})
        @Config.LangKey("config.tfcthings.items.hiking_boots")
        public static final HikingBootsCFG HIKING_BOOTS = new HikingBootsCFG();
        @Config.LangKey("config.tfcthings.items.rope_bridge")
        public static final RopeBridgeCFG ROPE_BRIDGE = new RopeBridgeCFG();
        @Config.Comment({"Enable/Disable All Items"})
        @Config.LangKey("config.tfcthings.items.masterlist")
        public static final MasterItemCFG MASTER_ITEM_LIST = new MasterItemCFG();

        public static final class SlingCFG {
            @Config.Comment({"Damage multiplier against predator animals and skeletons.", "New damage = sling damage * multiplier"})
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
            @Config.LangKey("config.tfcthings.items.fixedDamage")
            @Config.Comment({"The amount of damage points dealt by a bear trap.", "This will override the fractional health cut setting if set to a value greater than 0"})
            @Config.RangeDouble (
                    min = 0.0D
            )
            public double fixedDamage = 0.0D;
        }

        public static final class SnareCFG {
            @Config.Comment("Percent chance for a snare to break when harvested after being tripped.")
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.items.breakChance")
            public double breakChance = 0.2D;

            @Config.Comment({"The chance for a snare to capture a random small animal when loaded with bait. Happens on random block ticks."})
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.items.baitCaptureChance")
            public double baitCaptureChance = 0.05D;

            @Config.Comment({"The chance for a piece of bait in a snare to be consumed if the snare fails to capture a random animal."})
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.items.baitExpireChance")
            public double baitExpireChance = 0.05D;
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

            @Config.Comment("The amount of extra damage a weapon does when sharpened.")
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.damageBoost")
            public int damageBoost = 2;
        }

        public static final class SnowShoesCFG {
            @Config.Comment({"The number of ticks of walking through snow required to apply one damage to the shoes", "0 = snow shoes won't get damaged by walking through snow"})
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.damageTicks")
            public int damageTicks = 500;

            @Config.Comment({"The percentage of the TFC slowdown effect that the snow shoes will negate", "1 = no slowdown when walking through snow", "0 = the shoes are useless D:"})
            @Config.RangeDouble (
                    min = 0,
                    max = 1
            )
            @Config.LangKey("config.tfcthings.items.shoePower")
            public double shoePower = 1;

        }

        public static final class HikingBootsCFG {
            @Config.Comment({"The number of ticks of walking through plants required to apply one damage to the shoes", "0 = hiking boots won't get damaged by walking through plants"})
            @Config.RangeInt (
                    min = 0
            )
            @Config.LangKey("config.tfcthings.items.damageTicks")
            public int damageTicks = 500;

            @Config.Comment({"The percentage of the TFC slowdown effect that the hiking boots will negate", "1 = no slowdown when walking through plants", "0 = the boots are useless D:"})
            @Config.RangeDouble (
                    min = 0,
                    max = 1
            )
            @Config.LangKey("config.tfcthings.items.shoePower")
            public double shoePower = 1;

        }

        public static final class RopeBridgeCFG {
            @Config.RangeInt (
                    min = 1,
                    max = 64
            )
            @Config.Comment({"The max stack size for rope bridge bundles which also determines the maximum length of a thrown bridge"})
            @Config.LangKey("config.tfcthings.items.maxStackSize")
            public int maxStackSize = 64;
        }

        public static final class MasterItemCFG {
            @Config.LangKey("config.tfcthings.enable.snowShoes")
            @Config.RequiresMcRestart
            public boolean enableSnowShoes = true;
            @Config.LangKey("config.tfcthings.enable.sling")
            @Config.RequiresMcRestart
            public boolean enableSling = true;
            @Config.LangKey("config.tfcthings.enable.crown")
            @Config.RequiresMcRestart
            public boolean enableCrowns = true;
            @Config.LangKey("config.tfcthings.enable.bearTrap")
            @Config.RequiresMcRestart
            public boolean enableBearTrap = true;
            @Config.LangKey("config.tfcthings.enable.pigvil")
            @Config.RequiresMcRestart
            public boolean enablePigvil = true;
            @Config.LangKey("config.tfcthings.enable.whetstone")
            @Config.RequiresMcRestart
            public boolean enableWhetstones = true;
            @Config.LangKey("config.tfcthings.enable.grindstone")
            @Config.RequiresMcRestart
            public boolean enableGrindstones = true;
            @Config.LangKey("config.tfcthings.enable.ropeJavelin")
            @Config.RequiresMcRestart
            public boolean enableRopeJavelins = true;
            @Config.LangKey("config.tfcthings.enable.hookJavelin")
            @Config.RequiresMcRestart
            public boolean enableHookJavelins = true;
            @Config.LangKey("config.tfcthings.enable.prohammer")
            @Config.RequiresMcRestart
            public boolean enableProspectorsHammer = true;
            @Config.LangKey("config.tfcthings.enable.snare")
            @Config.RequiresMcRestart
            public boolean enableSnare = true;
            @Config.LangKey("config.tfcthings.enable.ropeBridge")
            @Config.RequiresMcRestart
            public boolean enableRopeBridge = true;
            @Config.LangKey("config.tfcthings.enable.ropeLadder")
            @Config.RequiresMcRestart
            public boolean enableRopeLadder = true;
            @Config.LangKey("config.tfcthings.enable.gemDisplay")
            @Config.RequiresMcRestart
            public boolean enableGemDisplays = true;
            @Config.LangKey("config.tfcthings.enable.hikingBoots")
            @Config.RequiresMcRestart
            public boolean enableHikingBoots = true;
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
            public String[] dayList = new String[]{"APRIL2 MeteorFreak's Birthday", "APRIL10 Pakratt0013's Birthday", "NOVEMBER11 HonneyPlay's Birthday", "MARCH2 Firedale's Birthday"};
        }

        public static final class PigvilCFG {
            @Config.Comment("The percent chance to create a Pigvil when feeding a pig iron carrot to a male pig")
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.misc.convertChance")
            public double convertChance = 0.25D;

            @Config.Comment({"The level of familiarity required for a male pig to be eligible for conversion into a Pigvil.", "The default value, 0.35 is the adult familiarity cap", "Levels higher than 0.35 require raising a baby pig", "Set to 0 to require no familiarity"})
            @Config.RangeDouble (
                    min = 0.0D,
                    max = 1.0D
            )
            @Config.LangKey("config.tfcthings.misc.familiarityLevel")
            public double familiarityLevel = 0.35D;
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
