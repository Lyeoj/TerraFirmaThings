package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TFCThings.MODID)
public class RegistryHandlerCommon {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(TFCThingsItems.ITEMLIST);
    }

    @SubscribeEvent
    public static void registerLeatherRecipes(RegistryEvent.Register<KnappingRecipe> event) {
        event.getRegistry().register(new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(TFCThingsItems.ITEM_SLING),
                "  XXX", "    X", " XXXX", "XX   ", "X    ").setRegistryName("sling"));
    }

}
