package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.init.TFCThingsSoundEvents;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.tileentity.TileEntityBearTrap;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.types.DefaultMetals;
import net.dries007.tfc.types.DefaultRocks;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.api.types.Metal.ItemType.INGOT;
import static net.dries007.tfc.api.types.Metal.ItemType.SHEET;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.GENERAL;

@Mod.EventBusSubscriber(modid = TFCThings.MODID)
public class RegistryHandlerCommon {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(TFCThingsItems.ITEMLIST);
        event.getRegistry().register(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_FIRED);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(TFCThingsBlocks.BLOCKLIST);
        GameRegistry.registerTileEntity(TileEntityBearTrap.class, TFCThingsBlocks.BEAR_TRAP.getRegistryName());
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(TFCThingsSoundEvents.SOUND_EVENTS);
    }

    @SubscribeEvent
    public static void registerKnappingRecipes(RegistryEvent.Register<KnappingRecipe> event) {
        event.getRegistry().register(new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(TFCThingsItems.ITEM_SLING),
                "  XXX", "    X", " XXXX", "XX   ", "X    ").setRegistryName("sling"));
        event.getRegistry().register(new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED),
                "XXXXX", " XXX ", "     ", " X X ", "XXXXX").setRegistryName("prospectors_hammer_clay_mold"));
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
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"bear_trap_half"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,SHEET))),
                new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF), Metal.STEEL.getTier(), GENERAL, HIT_LAST, DRAW_SECOND_LAST, SHRINK_THIRD_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"pig_iron_carrot"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.PIG_IRON,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PIG_IRON_CARROT), Metal.PIG_IRON.getTier(), GENERAL, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(
                new ResourceLocation(TFCThings.MODID + ":honing_steel_head"),
                IIngredient.of(OreDictionaryHelper.toString(new Object[]{"ingot", "double", DefaultMetals.BLACK_STEEL.getPath()})),
                new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD), Metal.Tier.TIER_V, SmithingSkill.Type.TOOLS,
                SHRINK_LAST, HIT_NOT_LAST, DRAW_NOT_LAST));

        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_bismuth_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BISMUTH_BRONZE,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BISMUTH_BRONZE), Metal.BISMUTH_BRONZE.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_black_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLACK_BRONZE,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_BRONZE), Metal.BLACK_BRONZE.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_black_steel"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL),INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_STEEL), TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL).getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_blue_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLUE_STEEL,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLUE_STEEL), Metal.BLUE_STEEL.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BRONZE,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BRONZE), Metal.BRONZE.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_copper"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.COPPER),INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_COPPER), TFCRegistries.METALS.getValue(DefaultMetals.COPPER).getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_red_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.RED_STEEL,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_RED_STEEL), Metal.RED_STEEL.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_STEEL), Metal.STEEL.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_wrought_iron"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_WROUGHT_IRON), Metal.WROUGHT_IRON.getTier(), GENERAL, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));

    }

    @SubscribeEvent
    public static void registerWeldingRecipes(RegistryEvent.Register<WeldingRecipe> event) {
        event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "bear_trap"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF)), new ItemStack(TFCThingsBlocks.BEAR_TRAP), Metal.STEEL.getTier()));
        event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "honing_steel_head_diamond"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_DIAMOND_GRIT)), new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD_DIAMOND), Metal.Tier.TIER_V));
        event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "honing_steel_diamond"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_HONING_STEEL)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_DIAMOND_GRIT)), new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_DIAMOND), Metal.Tier.TIER_V));
    }

    @SubscribeEvent
    public static void registerQuernRecipes(RegistryEvent.Register<QuernRecipe> event) {
        event.getRegistry().register(new QuernRecipe(IIngredient.of(ItemBrickTFC.get(TFCRegistries.ROCKS.getValue(DefaultRocks.QUARTZITE))), new ItemStack((TFCThingsItems.ITEM_WHETSTONE))).setRegistryName("whetstone"));
    }

    @SubscribeEvent
    public static void registerHeatRecipes(RegistryEvent.Register<HeatRecipe> event) {
        event.getRegistry().register(new HeatRecipeSimple(IIngredient.of(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED), new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_FIRED), 1599.0F, Metal.Tier.TIER_I).setRegistryName("prospectors_hammer_clay_mold_fired"));
    }

}
