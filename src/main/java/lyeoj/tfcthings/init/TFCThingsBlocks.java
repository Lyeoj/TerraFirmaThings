package lyeoj.tfcthings.init;

import lyeoj.tfcthings.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TFCThingsBlocks {

    public static final Block BEAR_TRAP = new BlockBearTrap();
    public static final Item BEAR_TRAP_ITEM = new ItemBlock(BEAR_TRAP).setRegistryName(BEAR_TRAP.getRegistryName()).setMaxStackSize(2);
    public static final Block PIGVIL_BLOCK = new BlockPigvil();
    public static final Item PIGVIL_ITEM = new ItemBlock(PIGVIL_BLOCK).setRegistryName(PIGVIL_BLOCK.getRegistryName());
    public static final Block METAL_SUPPORT_BLOCK = new BlockMetalSupport(null);
    public static final Item METAL_SUPPORT_ITEM = new ItemBlock(METAL_SUPPORT_BLOCK).setRegistryName(METAL_SUPPORT_BLOCK.getRegistryName());
    public static final Block SNARE_BLOCK = new BlockSnare();
    public static final Item SNARE_ITEM = new ItemBlock(SNARE_BLOCK).setRegistryName(SNARE_BLOCK.getRegistryName()).setMaxStackSize(4);
    public static final Block ROPE_BRIDGE_BLOCK = new BlockRopeBridge();
    public static final Item ROPE_BRIDGE_ITEM = new ItemBlock(ROPE_BRIDGE_BLOCK).setRegistryName(ROPE_BRIDGE_BLOCK.getRegistryName());
    public static final Block ROPE_LADDER_BLOCK = new BlockRopeLadder();
    public static final Item ROPE_LADDER_ITEM = new ItemBlock(ROPE_LADDER_BLOCK).setRegistryName(ROPE_LADDER_BLOCK.getRegistryName());

    public static final Block[] BLOCKLIST = {
            BEAR_TRAP,
            PIGVIL_BLOCK,
            METAL_SUPPORT_BLOCK,
            SNARE_BLOCK,
            ROPE_BRIDGE_BLOCK,
            ROPE_LADDER_BLOCK
    };
}
