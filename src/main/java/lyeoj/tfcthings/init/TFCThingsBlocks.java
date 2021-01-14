package lyeoj.tfcthings.init;

import lyeoj.tfcthings.blocks.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TFCThingsBlocks {

    public static final Block BEAR_TRAP = new BlockBearTrap();
    public static final Item BEAR_TRAP_ITEM = new ItemBlock(BEAR_TRAP).setRegistryName(BEAR_TRAP.getRegistryName()).setMaxStackSize(2);
    public static final Block PIGVIL_BLOCK = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.STEEL));
    public static final Item PIGVIL_ITEM = new ItemBlock(PIGVIL_BLOCK).setRegistryName(PIGVIL_BLOCK.getRegistryName());
    public static final Block PIGVIL_BLOCK_BLACK = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL));
    public static final Item PIGVIL_ITEM_BLACK = new ItemBlock(PIGVIL_BLOCK_BLACK).setRegistryName(PIGVIL_BLOCK_BLACK.getRegistryName());
    public static final Block PIGVIL_BLOCK_RED = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL));
    public static final Item PIGVIL_ITEM_RED = new ItemBlock(PIGVIL_BLOCK_RED).setRegistryName(PIGVIL_BLOCK_RED.getRegistryName());
    public static final Block PIGVIL_BLOCK_BLUE = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL));
    public static final Item PIGVIL_ITEM_BLUE = new ItemBlock(PIGVIL_BLOCK_BLUE).setRegistryName(PIGVIL_BLOCK_BLUE.getRegistryName());
    public static final Block PIGVIL_BLOCK_PURPLE = new BlockPigvil();
    public static final Item PIGVIL_ITEM_PURPLE = new ItemBlock(PIGVIL_BLOCK_PURPLE).setRegistryName(PIGVIL_BLOCK_PURPLE.getRegistryName());
    public static final Block SNARE_BLOCK = new BlockSnare();
    public static final Item SNARE_ITEM = new ItemBlock(SNARE_BLOCK).setRegistryName(SNARE_BLOCK.getRegistryName()).setMaxStackSize(4);
    public static final Block ROPE_BRIDGE_BLOCK = new BlockRopeBridge();
    public static final Item ROPE_BRIDGE_ITEM = new ItemBlock(ROPE_BRIDGE_BLOCK).setRegistryName(ROPE_BRIDGE_BLOCK.getRegistryName());
    public static final Block ROPE_LADDER_BLOCK = new BlockRopeLadder();
    public static final Item ROPE_LADDER_ITEM = new ItemBlock(ROPE_LADDER_BLOCK).setRegistryName(ROPE_LADDER_BLOCK.getRegistryName());
    public static final Block GRINDSTONE_BLOCK = new BlockGrindstone();
    public static final Item GRINDSTONE_ITEM = new ItemBlock(GRINDSTONE_BLOCK).setRegistryName(GRINDSTONE_BLOCK.getRegistryName()).setMaxStackSize(4);

    public static final Block GEM_DISPLAY_ANDESITE = new BlockGemDisplay("andesite");
    public static final Item GEM_DISPLAY_ANDESITE_ITEM = new ItemBlock(GEM_DISPLAY_ANDESITE).setRegistryName(GEM_DISPLAY_ANDESITE.getRegistryName());
    public static final Block GEM_DISPLAY_BASALT = new BlockGemDisplay("basalt");
    public static final Item GEM_DISPLAY_BASALT_ITEM = new ItemBlock(GEM_DISPLAY_BASALT).setRegistryName(GEM_DISPLAY_BASALT.getRegistryName());
    public static final Block GEM_DISPLAY_CHALK = new BlockGemDisplay("chalk");
    public static final Item GEM_DISPLAY_CHALK_ITEM = new ItemBlock(GEM_DISPLAY_CHALK).setRegistryName(GEM_DISPLAY_CHALK.getRegistryName());
    public static final Block GEM_DISPLAY_CHERT = new BlockGemDisplay("chert");
    public static final Item GEM_DISPLAY_CHERT_ITEM = new ItemBlock(GEM_DISPLAY_CHERT).setRegistryName(GEM_DISPLAY_CHERT.getRegistryName());
    public static final Block GEM_DISPLAY_CLAYSTONE = new BlockGemDisplay("claystone");
    public static final Item GEM_DISPLAY_CLAYSTONE_ITEM = new ItemBlock(GEM_DISPLAY_CLAYSTONE).setRegistryName(GEM_DISPLAY_CLAYSTONE.getRegistryName());
    public static final Block GEM_DISPLAY_CONGLOMERATE = new BlockGemDisplay("conglomerate");
    public static final Item GEM_DISPLAY_CONGLOMERATE_ITEM = new ItemBlock(GEM_DISPLAY_CONGLOMERATE).setRegistryName(GEM_DISPLAY_CONGLOMERATE.getRegistryName());
    public static final Block GEM_DISPLAY_DACITE = new BlockGemDisplay("dacite");
    public static final Item GEM_DISPLAY_DACITE_ITEM = new ItemBlock(GEM_DISPLAY_DACITE).setRegistryName(GEM_DISPLAY_DACITE.getRegistryName());
    public static final Block GEM_DISPLAY_DIORITE = new BlockGemDisplay("diorite");
    public static final Item GEM_DISPLAY_DIORITE_ITEM = new ItemBlock(GEM_DISPLAY_DIORITE).setRegistryName(GEM_DISPLAY_DIORITE.getRegistryName());
    public static final Block GEM_DISPLAY_DOLOMITE = new BlockGemDisplay("dolomite");
    public static final Item GEM_DISPLAY_DOLOMITE_ITEM = new ItemBlock(GEM_DISPLAY_DOLOMITE).setRegistryName(GEM_DISPLAY_DOLOMITE.getRegistryName());
    public static final Block GEM_DISPLAY_GABBRO = new BlockGemDisplay("gabbro");
    public static final Item GEM_DISPLAY_GABBRO_ITEM = new ItemBlock(GEM_DISPLAY_GABBRO).setRegistryName(GEM_DISPLAY_GABBRO.getRegistryName());
    public static final Block GEM_DISPLAY_GNEISS = new BlockGemDisplay("gneiss");
    public static final Item GEM_DISPLAY_GNEISS_ITEM = new ItemBlock(GEM_DISPLAY_GNEISS).setRegistryName(GEM_DISPLAY_GNEISS.getRegistryName());
    public static final Block GEM_DISPLAY_GRANITE = new BlockGemDisplay("granite");
    public static final Item GEM_DISPLAY_GRANITE_ITEM = new ItemBlock(GEM_DISPLAY_GRANITE).setRegistryName(GEM_DISPLAY_GRANITE.getRegistryName());
    public static final Block GEM_DISPLAY_LIMESTONE = new BlockGemDisplay("limestone");
    public static final Item GEM_DISPLAY_LIMESTONE_ITEM = new ItemBlock(GEM_DISPLAY_LIMESTONE).setRegistryName(GEM_DISPLAY_LIMESTONE.getRegistryName());
    public static final Block GEM_DISPLAY_MARBLE = new BlockGemDisplay("marble");
    public static final Item GEM_DISPLAY_MARBLE_ITEM = new ItemBlock(GEM_DISPLAY_MARBLE).setRegistryName(GEM_DISPLAY_MARBLE.getRegistryName());
    public static final Block GEM_DISPLAY_PHYLLITE = new BlockGemDisplay("phyllite");
    public static final Item GEM_DISPLAY_PHYLLITE_ITEM = new ItemBlock(GEM_DISPLAY_PHYLLITE).setRegistryName(GEM_DISPLAY_PHYLLITE.getRegistryName());
    public static final Block GEM_DISPLAY_QUARTZITE = new BlockGemDisplay("quartzite");
    public static final Item GEM_DISPLAY_QUARTZITE_ITEM = new ItemBlock(GEM_DISPLAY_QUARTZITE).setRegistryName(GEM_DISPLAY_QUARTZITE.getRegistryName());
    public static final Block GEM_DISPLAY_RHYOLITE = new BlockGemDisplay("rhyolite");
    public static final Item GEM_DISPLAY_RHYOLITE_ITEM = new ItemBlock(GEM_DISPLAY_RHYOLITE).setRegistryName(GEM_DISPLAY_RHYOLITE.getRegistryName());
    public static final Block GEM_DISPLAY_ROCKSALT = new BlockGemDisplay("rocksalt");
    public static final Item GEM_DISPLAY_ROCKSALT_ITEM = new ItemBlock(GEM_DISPLAY_ROCKSALT).setRegistryName(GEM_DISPLAY_ROCKSALT.getRegistryName());
    public static final Block GEM_DISPLAY_SCHIST = new BlockGemDisplay("schist");
    public static final Item GEM_DISPLAY_SCHIST_ITEM = new ItemBlock(GEM_DISPLAY_SCHIST).setRegistryName(GEM_DISPLAY_SCHIST.getRegistryName());
    public static final Block GEM_DISPLAY_SHALE = new BlockGemDisplay("shale");
    public static final Item GEM_DISPLAY_SHALE_ITEM = new ItemBlock(GEM_DISPLAY_SHALE).setRegistryName(GEM_DISPLAY_SHALE.getRegistryName());
    public static final Block GEM_DISPLAY_SLATE = new BlockGemDisplay("slate");
    public static final Item GEM_DISPLAY_SLATE_ITEM = new ItemBlock(GEM_DISPLAY_SLATE).setRegistryName(GEM_DISPLAY_SLATE.getRegistryName());

    public static final Block[] BLOCKLIST = {
            BEAR_TRAP,
            PIGVIL_BLOCK,
            PIGVIL_BLOCK_BLACK,
            PIGVIL_BLOCK_BLUE,
            PIGVIL_BLOCK_RED,
            PIGVIL_BLOCK_PURPLE,
            SNARE_BLOCK,
            ROPE_BRIDGE_BLOCK,
            ROPE_LADDER_BLOCK,
            GRINDSTONE_BLOCK,
            GEM_DISPLAY_ANDESITE,
            GEM_DISPLAY_BASALT,
            GEM_DISPLAY_CHALK,
            GEM_DISPLAY_CHERT,
            GEM_DISPLAY_CLAYSTONE,
            GEM_DISPLAY_CONGLOMERATE,
            GEM_DISPLAY_DACITE,
            GEM_DISPLAY_DIORITE,
            GEM_DISPLAY_DOLOMITE,
            GEM_DISPLAY_GABBRO,
            GEM_DISPLAY_GNEISS,
            GEM_DISPLAY_GRANITE,
            GEM_DISPLAY_LIMESTONE,
            GEM_DISPLAY_MARBLE,
            GEM_DISPLAY_PHYLLITE,
            GEM_DISPLAY_QUARTZITE,
            GEM_DISPLAY_RHYOLITE,
            GEM_DISPLAY_ROCKSALT,
            GEM_DISPLAY_SCHIST,
            GEM_DISPLAY_SHALE,
            GEM_DISPLAY_SLATE
    };
}
