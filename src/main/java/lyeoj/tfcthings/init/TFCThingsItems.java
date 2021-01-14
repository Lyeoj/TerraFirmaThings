package lyeoj.tfcthings.init;

import lyeoj.tfcthings.items.*;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import java.util.HashMap;
import java.util.Map;

public class TFCThingsItems {

    public static final Map<String, Map<Metal, Item>> TOOLS_HEADS_BY_METAL = new HashMap<>();

    public static final IArmorMaterialTFC SNOW_SHOES_MATERIAL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("snow_shoes", "tfcthings:snow_shoes", 14, new int[]{1, 0, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 5.0F, 5.0F, 10.0F);
    public static final Item ITEM_SNOWSHOES = new ItemSnowShoes(SNOW_SHOES_MATERIAL, 0, EntityEquipmentSlot.FEET);
    public static final IArmorMaterialTFC HIKING_BOOTS_MATERIAL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("hiking_boots", "tfcthings:hiking_boots", 28, new int[]{1, 0, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 5.0F, 5.0F, 10.0F);
    public static final Item ITEM_HIKING_BOOTS = new ItemHikingBoots(HIKING_BOOTS_MATERIAL, 0, EntityEquipmentSlot.FEET);
    public static final Item ITEM_SLING = new ItemSling(0).setTranslationKey("sling").setRegistryName("sling");;
    public static final Item ITEM_SLING_METAL = new ItemSling(1).setTranslationKey("sling_metal").setRegistryName("sling_metal");
    public static final Item ITEM_SLING_AMMO = new ItemMetalSlingAmmo(0).setTranslationKey("sling_ammo").setRegistryName("sling_ammo").setMaxStackSize(32);
    public static final Item ITEM_SLING_AMMO_SPREAD = new ItemMetalSlingAmmo(1).setTranslationKey("sling_ammo_spread").setRegistryName("sling_ammo_spread");
    public static final Item ITEM_SLING_AMMO_LIGHT = new ItemMetalSlingAmmo(2).setTranslationKey("sling_ammo_light").setRegistryName("sling_ammo_light");
    public static final Item ITEM_SLING_AMMO_FIRE = new ItemMetalSlingAmmo(3).setTranslationKey("sling_ammo_fire").setRegistryName("sling_ammo_fire");
    public static final Item ITEM_BEAR_TRAP_HALF = new ItemBearTrapHalf();

    public static final Item ITEM_METAL_BRACING = new ItemMetalBracing();
    public static final Item ITEM_ROPE_BRIDGE = new ItemRopeBridge();

    public static final Item ITEM_WHETSTONE = new ItemWhetstone(1, 64).setRegistryName("whetstone").setTranslationKey("whetstone");
    public static final Item ITEM_HONING_STEEL = new ItemWhetstone(2, 4200).setRegistryName("honing_steel").setTranslationKey("honing_steel");
    public static final Item ITEM_HONING_STEEL_DIAMOND = new ItemWhetstone(3, 4500).setRegistryName("honing_steel_diamond").setTranslationKey("honing_steel_diamond");
    public static final Item ITEM_HONING_STEEL_HEAD = new ItemTFCThingsToolHead(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones,"honing_steel_head");
    public static final Item ITEM_HONING_STEEL_HEAD_DIAMOND = new ItemTFCThingsToolHead(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones, "honing_steel_head_diamond");
    public static final Item ITEM_DIAMOND_GRIT = new ItemDiamondGrit();

    public static final Item ITEM_GRINDSTONE_QUARTZ = new ItemGrindstone(1, 640, "grindstone_quartz");
    public static final Item ITEM_GRINDSTONE_STEEL = new ItemGrindstone(2, 6400, "grindstone_steel");
    public static final Item ITEM_GRINDSTONE_DIAMOND = new ItemGrindstone(3, 7000, "grindstone_diamond");

    public static final Item ITEM_PIG_IRON_CARROT = new ItemPigIronCarrot(TFCRegistries.METALS.getValue(DefaultMetals.PIG_IRON)).setRegistryName("pig_iron_carrot").setTranslationKey("pig_iron_carrot");
    public static final Item ITEM_BLACK_STEEL_CARROT = new ItemPigIronCarrot(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL)).setRegistryName("black_steel_carrot").setTranslationKey("black_steel_carrot");
    public static final Item ITEM_BLUE_STEEL_CARROT = new ItemPigIronCarrot(TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)).setRegistryName("blue_steel_carrot").setTranslationKey("blue_steel_carrot");
    public static final Item ITEM_RED_STEEL_CARROT = new ItemPigIronCarrot(TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL)).setRegistryName("red_steel_carrot").setTranslationKey("red_steel_carrot");

    public static final Item ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED = new ItemPottery().setRegistryName("mold/unfired/prospectors_hammer_head").setTranslationKey("mold.unfired.prospectors_hammer_head");
    public static final Item ITEM_PROSPECTORS_HAMMER_MOLD_FIRED = new ItemTFCThingsMold("prospectors_hammer_head");

    public static final Item ITEM_PROSPECTORS_HAMMER_BISMUTH_BRONZE = new ItemProspectorsHammer(Metal.BISMUTH_BRONZE, "bismuth_bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_BLACK_BRONZE = new ItemProspectorsHammer(Metal.BLACK_BRONZE, "black_bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_BLACK_STEEL = new ItemProspectorsHammer(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), "black_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_BLUE_STEEL = new ItemProspectorsHammer(Metal.BLUE_STEEL, "blue_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_BRONZE = new ItemProspectorsHammer(Metal.BRONZE, "bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_COPPER = new ItemProspectorsHammer(TFCRegistries.METALS.getValue(DefaultMetals.COPPER), "copper");
    public static final Item ITEM_PROSPECTORS_HAMMER_RED_STEEL = new ItemProspectorsHammer(Metal.RED_STEEL, "red_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_STEEL = new ItemProspectorsHammer(Metal.STEEL, "steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_WROUGHT_IRON = new ItemProspectorsHammer(Metal.WROUGHT_IRON, "wrought_iron");

    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_BISMUTH_BRONZE = new ItemTFCThingsToolHead(Metal.BISMUTH_BRONZE, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "bismuth_bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_BRONZE = new ItemTFCThingsToolHead(Metal.BLACK_BRONZE, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "black_bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_STEEL = new ItemTFCThingsToolHead(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "black_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_BLUE_STEEL = new ItemTFCThingsToolHead(Metal.BLUE_STEEL, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "blue_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_BRONZE = new ItemTFCThingsToolHead(Metal.BRONZE, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "bronze");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_COPPER = new ItemTFCThingsToolHead(TFCRegistries.METALS.getValue(DefaultMetals.COPPER), 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "copper");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_RED_STEEL = new ItemTFCThingsToolHead(Metal.RED_STEEL, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "red_steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_STEEL = new ItemTFCThingsToolHead(Metal.STEEL, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "steel");
    public static final Item ITEM_PROSPECTORS_HAMMER_HEAD_WROUGHT_IRON = new ItemTFCThingsToolHead(Metal.WROUGHT_IRON, 100, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer, "prospectors_hammer_head", "wrought_iron");

    public static final Item ITEM_ROPE_JAVELIN_BISMUTH_BRONZE = new ItemRopeJavelin(Metal.BISMUTH_BRONZE, "bismuth_bronze");
    public static final Item ITEM_ROPE_JAVELIN_BLACK_BRONZE = new ItemRopeJavelin(Metal.BLACK_BRONZE, "black_bronze");
    public static final Item ITEM_ROPE_JAVELIN_BLACK_STEEL = new ItemRopeJavelin(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), "black_steel");
    public static final Item ITEM_ROPE_JAVELIN_BLUE_STEEL = new ItemRopeJavelin(Metal.BLUE_STEEL, "blue_steel");
    public static final Item ITEM_ROPE_JAVELIN_BRONZE = new ItemRopeJavelin(Metal.BRONZE, "bronze");
    public static final Item ITEM_ROPE_JAVELIN_COPPER = new ItemRopeJavelin(TFCRegistries.METALS.getValue(DefaultMetals.COPPER), "copper");
    public static final Item ITEM_ROPE_JAVELIN_RED_STEEL = new ItemRopeJavelin(Metal.RED_STEEL, "red_steel");
    public static final Item ITEM_ROPE_JAVELIN_STEEL = new ItemRopeJavelin(Metal.STEEL, "steel");
    public static final Item ITEM_ROPE_JAVELIN_WROUGHT_IRON = new ItemRopeJavelin(Metal.WROUGHT_IRON, "wrought_iron");

    public static final Item ITEM_HOOK_JAVELIN_HEAD_BLACK_STEEL = new ItemTFCThingsToolHead(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins, "hook_javelin_head", "black_steel");
    public static final Item ITEM_HOOK_JAVELIN_HEAD_BLUE_STEEL = new ItemTFCThingsToolHead(Metal.BLUE_STEEL, 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins, "hook_javelin_head", "blue_steel");
    public static final Item ITEM_HOOK_JAVELIN_HEAD_RED_STEEL = new ItemTFCThingsToolHead(Metal.RED_STEEL, 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins, "hook_javelin_head", "red_steel");
    public static final Item ITEM_HOOK_JAVELIN_HEAD_STEEL = new ItemTFCThingsToolHead(Metal.STEEL, 200, ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins, "hook_javelin_head", "steel");

    public static final Item ITEM_HOOK_JAVELIN_BLACK_STEEL = new ItemHookJavelin(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), "black_steel");
    public static final Item ITEM_HOOK_JAVELIN_BLUE_STEEL = new ItemHookJavelin(Metal.BLUE_STEEL, "blue_steel");
    public static final Item ITEM_HOOK_JAVELIN_RED_STEEL = new ItemHookJavelin(Metal.RED_STEEL, "red_steel");
    public static final Item ITEM_HOOK_JAVELIN_STEEL = new ItemHookJavelin(Metal.STEEL, "steel");

    public static final Item ITEM_GOLD_CROWN_EMPTY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold");
    public static final Item ITEM_GOLD_CROWN_AGATE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.AGATE);
    public static final Item ITEM_GOLD_CROWN_AMETHYST = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.AMETHYST);
    public static final Item ITEM_GOLD_CROWN_BERYL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.BERYL);
    public static final Item ITEM_GOLD_CROWN_DIAMOND = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.DIAMOND);
    public static final Item ITEM_GOLD_CROWN_EMERALD = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.EMERALD);
    public static final Item ITEM_GOLD_CROWN_GARNET = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.GARNET);
    public static final Item ITEM_GOLD_CROWN_JADE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.JADE);
    public static final Item ITEM_GOLD_CROWN_JASPER = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.JASPER);
    public static final Item ITEM_GOLD_CROWN_OPAL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.OPAL);
    public static final Item ITEM_GOLD_CROWN_RUBY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.RUBY);
    public static final Item ITEM_GOLD_CROWN_SAPPHIRE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.SAPPHIRE);
    public static final Item ITEM_GOLD_CROWN_TOPAZ = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.TOPAZ);
    public static final Item ITEM_GOLD_CROWN_TOURMALINE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", Gem.TOURMALINE);

    public static final Item ITEM_PLATINUM_CROWN_EMPTY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum");
    public static final Item ITEM_PLATINUM_CROWN_AGATE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.AGATE);
    public static final Item ITEM_PLATINUM_CROWN_AMETHYST = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.AMETHYST);
    public static final Item ITEM_PLATINUM_CROWN_BERYL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.BERYL);
    public static final Item ITEM_PLATINUM_CROWN_DIAMOND = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.DIAMOND);
    public static final Item ITEM_PLATINUM_CROWN_EMERALD = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.EMERALD);
    public static final Item ITEM_PLATINUM_CROWN_GARNET = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.GARNET);
    public static final Item ITEM_PLATINUM_CROWN_JADE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.JADE);
    public static final Item ITEM_PLATINUM_CROWN_JASPER = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.JASPER);
    public static final Item ITEM_PLATINUM_CROWN_OPAL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.OPAL);
    public static final Item ITEM_PLATINUM_CROWN_RUBY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.RUBY);
    public static final Item ITEM_PLATINUM_CROWN_SAPPHIRE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.SAPPHIRE);
    public static final Item ITEM_PLATINUM_CROWN_TOPAZ = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.TOPAZ);
    public static final Item ITEM_PLATINUM_CROWN_TOURMALINE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", Gem.TOURMALINE);

    public static final Item[] ITEMLIST = {
            ITEM_WHETSTONE,
            ITEM_HONING_STEEL,
            ITEM_HONING_STEEL_DIAMOND,
            ITEM_HONING_STEEL_HEAD,
            ITEM_HONING_STEEL_HEAD_DIAMOND,
            ITEM_DIAMOND_GRIT,
            ITEM_GRINDSTONE_QUARTZ,
            ITEM_GRINDSTONE_STEEL,
            ITEM_GRINDSTONE_DIAMOND,
            ITEM_SNOWSHOES,
            ITEM_HIKING_BOOTS,
            ITEM_SLING,
            ITEM_SLING_METAL,
            ITEM_SLING_AMMO,
            ITEM_SLING_AMMO_SPREAD,
            ITEM_SLING_AMMO_LIGHT,
            ITEM_SLING_AMMO_FIRE,
            ITEM_BEAR_TRAP_HALF,
            ITEM_PIG_IRON_CARROT,
            ITEM_BLACK_STEEL_CARROT,
            ITEM_BLUE_STEEL_CARROT,
            ITEM_RED_STEEL_CARROT,
            ITEM_METAL_BRACING,
            ITEM_ROPE_BRIDGE,
            ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED,
            ITEM_PROSPECTORS_HAMMER_BISMUTH_BRONZE,
            ITEM_PROSPECTORS_HAMMER_BLACK_BRONZE,
            ITEM_PROSPECTORS_HAMMER_BLACK_STEEL,
            ITEM_PROSPECTORS_HAMMER_BLUE_STEEL,
            ITEM_PROSPECTORS_HAMMER_BRONZE,
            ITEM_PROSPECTORS_HAMMER_COPPER,
            ITEM_PROSPECTORS_HAMMER_RED_STEEL,
            ITEM_PROSPECTORS_HAMMER_STEEL,
            ITEM_PROSPECTORS_HAMMER_WROUGHT_IRON,
            ITEM_PROSPECTORS_HAMMER_HEAD_BISMUTH_BRONZE,
            ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_BRONZE,
            ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_STEEL,
            ITEM_PROSPECTORS_HAMMER_HEAD_BLUE_STEEL,
            ITEM_PROSPECTORS_HAMMER_HEAD_BRONZE,
            ITEM_PROSPECTORS_HAMMER_HEAD_COPPER,
            ITEM_PROSPECTORS_HAMMER_HEAD_RED_STEEL,
            ITEM_PROSPECTORS_HAMMER_HEAD_STEEL,
            ITEM_PROSPECTORS_HAMMER_HEAD_WROUGHT_IRON,
            ITEM_ROPE_JAVELIN_BISMUTH_BRONZE,
            ITEM_ROPE_JAVELIN_BLACK_BRONZE,
            ITEM_ROPE_JAVELIN_BLACK_STEEL,
            ITEM_ROPE_JAVELIN_BLUE_STEEL,
            ITEM_ROPE_JAVELIN_BRONZE,
            ITEM_ROPE_JAVELIN_COPPER,
            ITEM_ROPE_JAVELIN_RED_STEEL,
            ITEM_ROPE_JAVELIN_STEEL,
            ITEM_ROPE_JAVELIN_WROUGHT_IRON,
            ITEM_HOOK_JAVELIN_HEAD_BLACK_STEEL,
            ITEM_HOOK_JAVELIN_HEAD_BLUE_STEEL,
            ITEM_HOOK_JAVELIN_HEAD_RED_STEEL,
            ITEM_HOOK_JAVELIN_HEAD_STEEL,
            ITEM_HOOK_JAVELIN_BLACK_STEEL,
            ITEM_HOOK_JAVELIN_BLUE_STEEL,
            ITEM_HOOK_JAVELIN_RED_STEEL,
            ITEM_HOOK_JAVELIN_STEEL,
            ITEM_GOLD_CROWN_EMPTY,
            ITEM_GOLD_CROWN_AGATE,
            ITEM_GOLD_CROWN_AMETHYST,
            ITEM_GOLD_CROWN_BERYL,
            ITEM_GOLD_CROWN_DIAMOND,
            ITEM_GOLD_CROWN_EMERALD,
            ITEM_GOLD_CROWN_GARNET,
            ITEM_GOLD_CROWN_JADE,
            ITEM_GOLD_CROWN_JASPER,
            ITEM_GOLD_CROWN_OPAL,
            ITEM_GOLD_CROWN_RUBY,
            ITEM_GOLD_CROWN_SAPPHIRE,
            ITEM_GOLD_CROWN_TOPAZ,
            ITEM_GOLD_CROWN_TOURMALINE,
            ITEM_PLATINUM_CROWN_EMPTY,
            ITEM_PLATINUM_CROWN_AGATE,
            ITEM_PLATINUM_CROWN_AMETHYST,
            ITEM_PLATINUM_CROWN_BERYL,
            ITEM_PLATINUM_CROWN_DIAMOND,
            ITEM_PLATINUM_CROWN_EMERALD,
            ITEM_PLATINUM_CROWN_GARNET,
            ITEM_PLATINUM_CROWN_JADE,
            ITEM_PLATINUM_CROWN_JASPER,
            ITEM_PLATINUM_CROWN_OPAL,
            ITEM_PLATINUM_CROWN_RUBY,
            ITEM_PLATINUM_CROWN_SAPPHIRE,
            ITEM_PLATINUM_CROWN_TOPAZ,
            ITEM_PLATINUM_CROWN_TOURMALINE,

            TFCThingsBlocks.BEAR_TRAP_ITEM,
            TFCThingsBlocks.PIGVIL_ITEM,
            TFCThingsBlocks.PIGVIL_ITEM_BLACK,
            TFCThingsBlocks.PIGVIL_ITEM_BLUE,
            TFCThingsBlocks.PIGVIL_ITEM_RED,
            TFCThingsBlocks.PIGVIL_ITEM_PURPLE,
            TFCThingsBlocks.SNARE_ITEM,
            TFCThingsBlocks.ROPE_BRIDGE_ITEM,
            TFCThingsBlocks.ROPE_LADDER_ITEM,
            TFCThingsBlocks.GRINDSTONE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_ANDESITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_BASALT_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_CHALK_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_CHERT_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_CLAYSTONE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_CONGLOMERATE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_DACITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_DIORITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_DOLOMITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_GABBRO_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_GNEISS_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_GRANITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_LIMESTONE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_MARBLE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_PHYLLITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_QUARTZITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_RHYOLITE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_ROCKSALT_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_SCHIST_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_SHALE_ITEM,
            TFCThingsBlocks.GEM_DISPLAY_SLATE_ITEM
    };
}
