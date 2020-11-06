package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.tileentity.TileEntityGemDisplay;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockGemDisplay extends Block implements IItemSize, TFCThingsConfigurableItem {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool TOP = PropertyBool.create("top");

    public BlockGemDisplay(String rock) {
        super(Material.ROCK);
        this.setRegistryName("gem_display/" + rock);
        this.setTranslationKey("gem_display_" + rock);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(TOP, Boolean.valueOf(true)));
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.HEAVY;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, TOP});
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(TOP, meta / 4 % 2 != 0);
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() + ((Boolean)state.getValue(TOP) ? 4 : 0);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityGemDisplay();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityGemDisplay te = (TileEntityGemDisplay) Helpers.getTE(worldIn, pos, TileEntityGemDisplay.class);
            if (te != null) {
                return te.onRightClick(playerIn, hand);
            }
        }

        return true;
    }

    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntityGemDisplay te = (TileEntityGemDisplay)Helpers.getTE(worldIn, pos, TileEntityGemDisplay.class);
        if (te != null) {
            te.onBreakBlock();
        }
        super.breakBlock(worldIn, pos, state);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(fromPos.equals(pos.up())) {
            if(worldIn.getBlockState(fromPos).getBlock() instanceof BlockAir) {
                state = state.withProperty(TOP, Boolean.valueOf(true));
            } else {
                state = state.withProperty(TOP, Boolean.valueOf(false));
            }
            worldIn.setBlockState(pos, state, 2);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos){
        TileEntityGemDisplay te = (TileEntityGemDisplay) world.getTileEntity(pos);
        return (int)Math.floor(15 * ((double)te.getSize() / (double)te.getMaxStackSize()));
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGemDisplays;
    }
}
