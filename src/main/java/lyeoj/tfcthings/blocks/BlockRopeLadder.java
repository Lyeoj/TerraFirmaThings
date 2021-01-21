package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockRopeLadder extends Block implements TFCThingsConfigurableItem {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
    protected static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
    protected static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);

    public BlockRopeLadder() {
        super(Material.WOOD);
        this.setTranslationKey("rope_ladder");
        this.setRegistryName("rope_ladder");
        this.setSoundType(SoundType.WOOD);
        this.setHardness(0.5f);
        this.setHarvestLevel("axe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch ((EnumFacing)state.getValue(FACING)) {
            case NORTH:
                return LADDER_NORTH_AABB;
            case SOUTH:
                return LADDER_SOUTH_AABB;
            case WEST:
                return LADDER_WEST_AABB;
            case EAST:
            default:
                return LADDER_EAST_AABB;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4));
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockRopeLadder) {
            return this.getDefaultState().withProperty(FACING, worldIn.getBlockState(pos.down()).getValue(FACING));
        }
        if (facing.getAxis().isHorizontal() && this.canAttachTo(worldIn, pos.offset(facing.getOpposite()), facing)) {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                if (this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        }
    }

    private boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_) {
        IBlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
        boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
        return !flag && iblockstate.getBlockFaceShape(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower();
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        BlockPos nextPos = pos;
        for(int i = 0; i < 32; i++) {
            if(stack.getCount() <= 1) {
                break;
            }
            nextPos = nextPos.down();
            if(worldIn.getBlockState(nextPos).getBlock().isReplaceable(worldIn, nextPos) && nextPos.getY() >= 0) {
                worldIn.setBlockState(nextPos, TFCThingsBlocks.ROPE_LADDER_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)));
                if(!((EntityPlayer)placer).isCreative()) {
                    stack.shrink(1);
                }
            } else {
                return;
            }

        }
        if(!((EntityPlayer)placer).isCreative()) {
            stack.setCount(0);
        }
        ItemStack ladderStack = findMoreLadders((EntityPlayer)placer);
        nextPos = nextPos.down();
        while(!ladderStack.isEmpty()) {
            if(worldIn.getBlockState(nextPos).getBlock().isReplaceable(worldIn, nextPos) && nextPos.getY() >= 0) {
                worldIn.setBlockState(nextPos, TFCThingsBlocks.ROPE_LADDER_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)));
                if(!((EntityPlayer)placer).isCreative()) {
                    ladderStack.shrink(1);
                }
            } else {
                return;
            }
            nextPos = nextPos.down();
            if(ladderStack.isEmpty()) {
                ladderStack = findMoreLadders((EntityPlayer)placer);
            }
        }

    }

    private ItemStack findMoreLadders(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemBlock && ((ItemBlock)itemstack.getItem()).getBlock() instanceof BlockRopeLadder) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()) {
            if(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockRopeLadder && worldIn.getBlockState(pos.down()).getBlock() instanceof BlockRopeLadder) {
                return false;
            }
            BlockPos next = pos;
            if(!(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockRopeLadder)) {
                do {
                    giveLadderToPlayer(playerIn, next);
                    worldIn.setBlockToAir(next);
                    next = next.down();
                } while(worldIn.getBlockState(next).getBlock() instanceof BlockRopeLadder);
            } else {
                do {
                    giveLadderToPlayer(playerIn, next);
                    worldIn.setBlockToAir(next);
                    next = next.up();
                } while(worldIn.getBlockState(next).getBlock() instanceof BlockRopeLadder);
            }
            return true;
        }
        return false;
    }

    private void giveLadderToPlayer(EntityPlayer player, BlockPos pos) {
        ItemStack ladderStack = new ItemStack(TFCThingsBlocks.ROPE_LADDER_ITEM, 1);
        if(!player.inventory.addItemStackToInventory(ladderStack) && !player.world.isRemote) {
            InventoryHelper.spawnItemStack(player.world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ladderStack);
        }
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeLadder;
    }
}
