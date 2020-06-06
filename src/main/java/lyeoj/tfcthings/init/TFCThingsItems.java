package lyeoj.tfcthings.init;

import lyeoj.tfcthings.items.*;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class TFCThingsItems {

    public static final Item ITEM_WHETSTONE = new ItemWhetstone(1, 64).setRegistryName("whetstone").setTranslationKey("whetstone");
    public static final Item ITEM_HONING_STEEL = new ItemWhetstone(2, 4200).setRegistryName("honing_steel").setTranslationKey("honing_steel");
    public static final Item ITEM_HONING_STEEL_HEAD = new ItemHoningSteelHead();
    public static final IArmorMaterialTFC SNOW_SHOES_MATERIAL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("snow_shoes", "tfcthings:snow_shoes", 14, new int[]{1, 0, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 5.0F, 5.0F, 10.0F);
    public static final Item ITEM_SNOWSHOES = new ItemSnowShoes(SNOW_SHOES_MATERIAL, 0, EntityEquipmentSlot.FEET);
    public static final Item ITEM_SLING = new ItemSling();
    public static final Item ITEM_BEAR_TRAP_HALF = new ItemBearTrapHalf();
    public static final Item ITEM_PIG_IRON_CARROT = new ItemPigIronCarrot();

    public static final Item ITEM_GOLD_CROWN_EMPTY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Empty");
    public static final Item ITEM_GOLD_CROWN_AGATE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Agate");
    public static final Item ITEM_GOLD_CROWN_AMETHYST = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Amethyst");
    public static final Item ITEM_GOLD_CROWN_BERYL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Beryl");
    public static final Item ITEM_GOLD_CROWN_DIAMOND = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Diamond");
    public static final Item ITEM_GOLD_CROWN_EMERALD = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Emerald");
    public static final Item ITEM_GOLD_CROWN_GARNET = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Garnet");
    public static final Item ITEM_GOLD_CROWN_JADE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Jade");
    public static final Item ITEM_GOLD_CROWN_JASPER = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Jasper");
    public static final Item ITEM_GOLD_CROWN_OPAL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Opal");
    public static final Item ITEM_GOLD_CROWN_RUBY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Ruby");
    public static final Item ITEM_GOLD_CROWN_SAPPHIRE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Sapphire");
    public static final Item ITEM_GOLD_CROWN_TOPAZ = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Topaz");
    public static final Item ITEM_GOLD_CROWN_TOURMALINE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "gold", "Tourmaline");

    public static final Item ITEM_PLATINUM_CROWN_EMPTY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Empty");
    public static final Item ITEM_PLATINUM_CROWN_AGATE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Agate");
    public static final Item ITEM_PLATINUM_CROWN_AMETHYST = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Amethyst");
    public static final Item ITEM_PLATINUM_CROWN_BERYL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Beryl");
    public static final Item ITEM_PLATINUM_CROWN_DIAMOND = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Diamond");
    public static final Item ITEM_PLATINUM_CROWN_EMERALD = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Emerald");
    public static final Item ITEM_PLATINUM_CROWN_GARNET = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Garnet");
    public static final Item ITEM_PLATINUM_CROWN_JADE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Jade");
    public static final Item ITEM_PLATINUM_CROWN_JASPER = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Jasper");
    public static final Item ITEM_PLATINUM_CROWN_OPAL = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Opal");
    public static final Item ITEM_PLATINUM_CROWN_RUBY = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Ruby");
    public static final Item ITEM_PLATINUM_CROWN_SAPPHIRE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Sapphire");
    public static final Item ITEM_PLATINUM_CROWN_TOPAZ = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Topaz");
    public static final Item ITEM_PLATINUM_CROWN_TOURMALINE = new ItemCrown(0, EntityEquipmentSlot.HEAD, "platinum", "Tourmaline");

    public static final Item[] ITEMLIST = {
            ITEM_WHETSTONE,
            ITEM_HONING_STEEL,
            ITEM_HONING_STEEL_HEAD,
            ITEM_SNOWSHOES,
            ITEM_SLING,
            ITEM_BEAR_TRAP_HALF,
            ITEM_PIG_IRON_CARROT,
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
            TFCThingsBlocks.PIGVIL_ITEM
    };
}
