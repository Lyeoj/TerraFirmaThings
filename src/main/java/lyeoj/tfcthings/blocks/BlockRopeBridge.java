package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.init.TFCThingsItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockRopeBridge extends Block {

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

//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        state = state.cycleProperty(OFFSET);
//        worldIn.setBlockState(pos, state, 2);
//        return true;
//    }

}
