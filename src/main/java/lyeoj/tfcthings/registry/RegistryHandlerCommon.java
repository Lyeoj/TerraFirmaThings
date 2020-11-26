package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.init.TFCThingsSoundEvents;
import lyeoj.tfcthings.items.ItemOreDict;
import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.tileentity.TileEntityBearTrap;
import lyeoj.tfcthings.tileentity.TileEntityGemDisplay;
import lyeoj.tfcthings.tileentity.TileEntityGrindstone;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

@Mod.EventBusSubscriber(modid = TFCThings.MODID)
public class RegistryHandlerCommon {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_FIRED);
        for(int i = 0; i < TFCThingsItems.ITEMLIST.length; i++) {
            Item item = TFCThingsItems.ITEMLIST[i];
            if(item instanceof TFCThingsConfigurableItem) {
                if(((TFCThingsConfigurableItem)item).isEnabled()) {
                    event.getRegistry().register(item);
                    if(item instanceof ItemOreDict) {
                        ((ItemOreDict)item).initOreDict();
                    }
                }
            } else {
                if(item instanceof ItemBlock && ((ItemBlock)item).getBlock() instanceof TFCThingsConfigurableItem) {
                    if(((TFCThingsConfigurableItem)((ItemBlock)item).getBlock()).isEnabled()) {
                        event.getRegistry().register(item);
                    }
                } else {
                    event.getRegistry().register(item);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for(int i = 0; i < TFCThingsBlocks.BLOCKLIST.length; i++) {
            Block block = TFCThingsBlocks.BLOCKLIST[i];
            if(block instanceof TFCThingsConfigurableItem) {
                if(((TFCThingsConfigurableItem)block).isEnabled()) {
                    event.getRegistry().register(block);
                }
            } else {
                event.getRegistry().register(block);
            }
        }
        GameRegistry.registerTileEntity(TileEntityBearTrap.class, TFCThingsBlocks.BEAR_TRAP.getRegistryName());
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGemDisplays) {
            GameRegistry.registerTileEntity(TileEntityGemDisplay.class, TFCThingsBlocks.GEM_DISPLAY_ANDESITE.getRegistryName());
        }
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGrindstones) {
            GameRegistry.registerTileEntity(TileEntityGrindstone.class, TFCThingsBlocks.GRINDSTONE_BLOCK.getRegistryName());
        }
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(TFCThingsSoundEvents.SOUND_EVENTS);
    }

    @SubscribeEvent
    public static void registerKnappingRecipes(RegistryEvent.Register<KnappingRecipe> event) {
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSling) {
            event.getRegistry().register(new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(TFCThingsItems.ITEM_SLING),
                    "  XXX", "    X", " XXXX", "XX   ", "X    ").setRegistryName("sling"));
        }
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer) {
            event.getRegistry().register(new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED),
                    "XXXXX", " XXX ", "     ", " X X ", "XXXXX").setRegistryName("prospectors_hammer_clay_mold"));
        }
    }

    @SubscribeEvent
    public static void registerAnvilRecipes(RegistryEvent.Register<AnvilRecipe> event) {
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableCrowns) {
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

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableBearTrap) {
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"bear_trap_half"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,SHEET))),
                    new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF), Metal.STEEL.getTier(), GENERAL, HIT_LAST, DRAW_SECOND_LAST, SHRINK_THIRD_LAST));
        }

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enablePigvil) {
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"pig_iron_carrot"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.PIG_IRON,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PIG_IRON_CARROT), Metal.PIG_IRON.getTier(), GENERAL, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"black_steel_carrot"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL),INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_BLACK_STEEL_CARROT), TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL).getTier(), GENERAL, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"blue_steel_carrot"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLUE_STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_BLUE_STEEL_CARROT), Metal.BLUE_STEEL.getTier(), GENERAL, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"red_steel_carrot"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.RED_STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_RED_STEEL_CARROT), Metal.RED_STEEL.getTier(), GENERAL, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
        }

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones) {
            event.getRegistry().register(new AnvilRecipe(
                    new ResourceLocation(TFCThings.MODID + ":honing_steel_head"),
                    IIngredient.of(OreDictionaryHelper.toString(new Object[]{"ingot", "double", DefaultMetals.BLACK_STEEL.getPath()})),
                    new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD), Metal.Tier.TIER_V, GENERAL,
                    SHRINK_LAST, HIT_NOT_LAST, DRAW_NOT_LAST));
        }

        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"metal_bracing"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_METAL_BRACING, 1), Metal.WROUGHT_IRON.getTier(), GENERAL, BEND_LAST, HIT_NOT_LAST, DRAW_NOT_LAST));
        event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"metal_bracing_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,INGOT))),
                new ItemStack(TFCThingsItems.ITEM_METAL_BRACING, 2), Metal.STEEL.getTier(), GENERAL, BEND_LAST, HIT_NOT_LAST, DRAW_NOT_LAST));

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer) {
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_bismuth_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BISMUTH_BRONZE,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BISMUTH_BRONZE), Metal.BISMUTH_BRONZE.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_black_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLACK_BRONZE,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_BRONZE), Metal.BLACK_BRONZE.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_black_steel"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL),INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLACK_STEEL), TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL).getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_blue_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLUE_STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BLUE_STEEL), Metal.BLUE_STEEL.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_bronze"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BRONZE,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_BRONZE), Metal.BRONZE.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_copper"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.COPPER),INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_COPPER), TFCRegistries.METALS.getValue(DefaultMetals.COPPER).getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_red_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.RED_STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_RED_STEEL), Metal.RED_STEEL.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_STEEL), Metal.STEEL.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"prospectors_hammer_head_wrought_iron"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_HEAD_WROUGHT_IRON), Metal.WROUGHT_IRON.getTier(), TOOLS, PUNCH_LAST, DRAW_NOT_LAST, SHRINK_NOT_LAST));
        }

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins) {
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"hook_javelin_head_black_steel"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL),DOUBLE_INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_HOOK_JAVELIN_HEAD_BLACK_STEEL), TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL).getTier(), WEAPONS, DRAW_LAST, UPSET_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"hook_javelin_head_blue_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLUE_STEEL,DOUBLE_INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_HOOK_JAVELIN_HEAD_BLUE_STEEL), Metal.BLUE_STEEL.getTier(), WEAPONS, DRAW_LAST, UPSET_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"hook_javelin_head_red_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.RED_STEEL,DOUBLE_INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_HOOK_JAVELIN_HEAD_RED_STEEL), Metal.RED_STEEL.getTier(), WEAPONS, DRAW_LAST, UPSET_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"hook_javelin_head_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,DOUBLE_INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_HOOK_JAVELIN_HEAD_STEEL), Metal.STEEL.getTier(), WEAPONS, DRAW_LAST, UPSET_NOT_LAST, BEND_NOT_LAST));
        }

        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSling) {
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"sling_ammo_steel"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_SLING_AMMO, 16), Metal.STEEL.getTier(), WEAPONS, HIT_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
            event.getRegistry().register(new AnvilRecipe(new ResourceLocation(TFCThings.MODID,"sling_ammo_iron"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON,INGOT))),
                    new ItemStack(TFCThingsItems.ITEM_SLING_AMMO, 8), Metal.WROUGHT_IRON.getTier(), WEAPONS, HIT_LAST, HIT_NOT_LAST, BEND_NOT_LAST));
        }

    }

    @SubscribeEvent
    public static void registerWeldingRecipes(RegistryEvent.Register<WeldingRecipe> event) {
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableBearTrap) {
            event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "bear_trap"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_BEAR_TRAP_HALF)), new ItemStack(TFCThingsBlocks.BEAR_TRAP), Metal.STEEL.getTier()));
        }
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones) {
            event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "honing_steel_head_diamond"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_DIAMOND_GRIT)), new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_HEAD_DIAMOND), Metal.Tier.TIER_V));
            event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "honing_steel_diamond"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_HONING_STEEL)), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_DIAMOND_GRIT)), new ItemStack(TFCThingsItems.ITEM_HONING_STEEL_DIAMOND), Metal.Tier.TIER_V));
        }
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGrindstones) {
            event.getRegistry().register(new WeldingRecipe(new ResourceLocation(TFCThings.MODID, "grindstone_steel"), IIngredient.of(new ItemStack(TFCThingsItems.ITEM_GRINDSTONE_QUARTZ)), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL),DOUBLE_SHEET))), new ItemStack(TFCThingsItems.ITEM_GRINDSTONE_STEEL), Metal.Tier.TIER_V));
        }
    }

    @SubscribeEvent
    public static void registerQuernRecipes(RegistryEvent.Register<QuernRecipe> event) {
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableWhetstones) {
            event.getRegistry().register(new QuernRecipe(IIngredient.of(ItemBrickTFC.get(TFCRegistries.ROCKS.getValue(DefaultRocks.QUARTZITE))), new ItemStack((TFCThingsItems.ITEM_WHETSTONE))).setRegistryName("whetstone"));
        }
    }

    @SubscribeEvent
    public static void registerHeatRecipes(RegistryEvent.Register<HeatRecipe> event) {
        if(ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer) {
            event.getRegistry().register(new HeatRecipeSimple(IIngredient.of(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_UNFIRED), new ItemStack(TFCThingsItems.ITEM_PROSPECTORS_HAMMER_MOLD_FIRED), 1599.0F, Metal.Tier.TIER_I).setRegistryName("prospectors_hammer_clay_mold_fired"));
        }
    }

}
