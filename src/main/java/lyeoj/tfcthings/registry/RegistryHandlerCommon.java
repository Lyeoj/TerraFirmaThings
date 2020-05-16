package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.types.DefaultMetals;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

    @SubscribeEvent
    public static void registerAnvilRecipes(RegistryEvent.Register<AnvilRecipe> event) {
        event.getRegistry().register(new AnvilRecipe(
                new ResourceLocation(TFCThings.MODID + ":gold_crown"),
                IIngredient.of(OreDictionaryHelper.toString(new Object[]{"sheet", "double", DefaultMetals.GOLD.getPath()})),
                new ItemStack(TFCThingsItems.ITEM_GOLD_CROWN_EMPTY), Metal.Tier.TIER_II, SmithingSkill.Type.ARMOR,
                ForgeRule.HIT_LAST, ForgeRule.UPSET_SECOND_LAST, ForgeRule.SHRINK_THIRD_LAST));
        event.getRegistry().register(new AnvilRecipe(
                new ResourceLocation(TFCThings.MODID + ":platinum_crown"),
                IIngredient.of(OreDictionaryHelper.toString(new Object[]{"sheet", "double", DefaultMetals.PLATINUM.getPath()})),
                new ItemStack(TFCThingsItems.ITEM_PLATINUM_CROWN_EMPTY), Metal.Tier.TIER_II, SmithingSkill.Type.ARMOR,
                ForgeRule.HIT_LAST, ForgeRule.UPSET_SECOND_LAST, ForgeRule.SHRINK_THIRD_LAST));
    }

}
