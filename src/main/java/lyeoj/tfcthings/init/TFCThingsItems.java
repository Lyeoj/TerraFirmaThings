package lyeoj.tfcthings.init;

import lyeoj.tfcthings.items.ItemSling;
import lyeoj.tfcthings.items.ItemSnowShoes;
import lyeoj.tfcthings.items.ItemWhetstone;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class TFCThingsItems {

    public static final Item ITEM_WHETSTONE = new ItemWhetstone();
    public static final IArmorMaterialTFC SNOW_SHOES_MATERIAL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("snow_shoes", "tfcthings:snow_shoes", 14, new int[]{0, 0, 0, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 5.0F, 5.0F, 10.0F);
    public static final Item ITEM_SNOWSHOES = new ItemSnowShoes(SNOW_SHOES_MATERIAL, 0, EntityEquipmentSlot.FEET);
    public static final Item ITEM_SLING = new ItemSling();

    public static final Item[] ITEMLIST = {
            ITEM_WHETSTONE,
            ITEM_SNOWSHOES,
            ITEM_SLING
    };
}
