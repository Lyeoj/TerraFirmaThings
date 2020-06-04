package lyeoj.tfcthings.init;

import lyeoj.tfcthings.blocks.BlockBearTrap;

import lyeoj.tfcthings.blocks.BlockPigvil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TFCThingsBlocks {

    public static final Block BEAR_TRAP = new BlockBearTrap();
    public static final Item BEAR_TRAP_ITEM = new ItemBlock(BEAR_TRAP).setRegistryName(BEAR_TRAP.getRegistryName()).setMaxStackSize(2);
    public static final Block PIGVIL_BLOCK = new BlockPigvil();
    public static final Item PIGVIL_ITEM = new ItemBlock(PIGVIL_BLOCK).setRegistryName(PIGVIL_BLOCK.getRegistryName());

    public static final Block[] BLOCKLIST = {
            BEAR_TRAP,
            PIGVIL_BLOCK
    };
}
