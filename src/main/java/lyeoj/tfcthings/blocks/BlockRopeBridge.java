package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;

public class BlockRopeBridge extends Block implements TFCThingsConfigurableItem {

    public static final PropertyInteger OFFSET = PropertyInteger.create("offset", 0, 7);
    public static final PropertyBool AXIS = PropertyBool.create("axis");

    public BlockRopeBridge() {
        super(Material.WOOD);
        this.setTranslationKey("rope_bridge");
        this.setRegistryName("rope_bridge");
        this.setSoundType(SoundType.WOOD);
        this.setHardness(0.5f);
        this.setHarvestLevel("axe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(OFFSET, Integer.valueOf(0)).withProperty(AXIS, Boolean.valueOf(false)));
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        int i = ((Integer)blockState.getValue(OFFSET)).intValue();
        return new AxisAlignedBB(0, (double)((float)i * 0.125F), 0, 1, (double)((float)i * 0.125F + (3 * 0.0625F)), 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {OFFSET, AXIS});
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return TFCThingsItems.ITEM_ROPE_BRIDGE;
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(OFFSET, meta % 8).withProperty(AXIS, meta > 7);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public int getMetaFromState(IBlockState state) {
        return (state.getValue(OFFSET)) + (state.getValue(AXIS) ? 8 : 0);
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeBridge;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(OreDictionaryHelper.doesStackMatchOre(playerIn.getHeldItem(hand), "stickWood")) {
            state = state.cycleProperty(OFFSET);
            worldIn.setBlockState(pos, state, 2);
            return true;
        }
        if(playerIn.isSneaking()) {
            int dir = getDir(worldIn, pos, state);
            if(dir == 0) {
                return false;
            }
            ItemStack bridgeStack = new ItemStack(TFCThingsItems.ITEM_ROPE_BRIDGE, 0);
            BlockPos next = pos;
            while(worldIn.getBlockState(next).getBlock() instanceof BlockRopeBridge) {
                bridgeStack.grow(1);

                if(worldIn.getBlockState(moveInDirection(next, dir)).getBlock() instanceof BlockRopeBridge) {
                    worldIn.setBlockToAir(next);
                    next = moveInDirection(next, dir);
                } else if(worldIn.getBlockState(next).getValue(OFFSET) == 0) {
                    worldIn.setBlockToAir(next);
                    next = moveInDirection(next, dir).down();
                } else if(worldIn.getBlockState(next).getValue(OFFSET) == 7) {
                    worldIn.setBlockToAir(next);
                    next = moveInDirection(next, dir).up();
                }
            }
            playerIn.inventory.addItemStackToInventory(bridgeStack);
            return true;
        }
        return false;
    }

    private BlockPos moveInDirection(BlockPos pos, int dir) {
        switch(dir) {
            case 1:
                return pos.north();
            case 2:
                return pos.south();
            case 3:
                return pos.east();
            default:
                return pos.west();
        }
    }

    private int getDir(World worldIn, BlockPos pos, IBlockState state) {
        ArrayList<Integer> dirs = new ArrayList<>();
        BlockPos side1;
        BlockPos side2 ;
        if(state.getValue(AXIS)) {
            side1 = pos.north();
            side2 = pos.south();
        } else {
            side1 = pos.east();
            side2 = pos.west();
        }

        if(worldIn.getBlockState(side1).getBlock() instanceof BlockRopeBridge) {
            dirs.add(1);
        }
        if(worldIn.getBlockState(side2).getBlock() instanceof BlockRopeBridge) {
            dirs.add(2);
        }
        if(state.getValue(OFFSET) == 0) {
            if(worldIn.getBlockState(side1.down()).getBlock() instanceof BlockRopeBridge) {
                dirs.add(1);
            }
            if(worldIn.getBlockState(side2.down()).getBlock() instanceof BlockRopeBridge) {
                dirs.add(2);
            }
        }
        if(state.getValue(OFFSET) == 7) {
            if(worldIn.getBlockState(side1.up()).getBlock() instanceof BlockRopeBridge) {
                dirs.add(1);
            }
            if(worldIn.getBlockState(side2.up()).getBlock() instanceof BlockRopeBridge) {
                dirs.add(2);
            }
        }
        if(dirs.size() == 1) {
            return dirs.get(0) + (state.getValue(AXIS) ? 0 : 2);
        } else {
            return 0;
        }
    }

}
