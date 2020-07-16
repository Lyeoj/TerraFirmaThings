package lyeoj.tfcthings.blocks;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockSupport;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMetalSupport extends BlockSupport {
    public BlockMetalSupport(Tree wood) {
        super(null);
        setRegistryName("metal_support");
        setTranslationKey("metal_support");
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if(face == EnumFacing.UP) {
            return BlockFaceShape.SOLID;
        }
        return BlockFaceShape.UNDEFINED;
    }

    public boolean isFullBlock(IBlockState state) {
        return true;
    }

    public boolean isFullCube(IBlockState state) {
        return true;
    }

    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

}
