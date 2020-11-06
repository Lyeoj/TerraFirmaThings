package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.tileentity.TileEntityBearTrap;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.entity.animal.*;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;


public class BlockSnare extends Block implements IItemSize, TFCThingsConfigurableItem {

    protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
    public static final PropertyBool CLOSED = PropertyBool.create("closed");
    public static final PropertyBool BAITED = PropertyBool.create("baited");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public BlockSnare() {
        super(Material.WOOD);
        this.setTranslationKey("snare");
        this.setRegistryName("snare");
        this.setTickRandomly(true);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(CreativeTabsTFC.CT_MISC);
        this.setHardness(1.5f);
        this.setHarvestLevel("axe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BAITED, Boolean.valueOf(false)).withProperty(CLOSED, Boolean.valueOf(false)));

    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntityBearTrap createTileEntity(World world, IBlockState state) {
        return new TileEntityBearTrap();
    }

    public TileEntityBearTrap getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityBearTrap)world.getTileEntity(pos);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, BAITED, CLOSED});
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4)).withProperty(BAITED, meta / 4 % 2 != 0).withProperty(CLOSED, meta / 8 != 0);
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() + ((Boolean)state.getValue(BAITED) ? 4 : 0) + ((Boolean)state.getValue(CLOSED) ? 8 : 0);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
        return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (double)((float)0 * 0.125F), axisalignedbb.maxZ);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != Blocks.BARRIER)
        {
            BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
            return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down());
        }
        else
        {
            return false;
        }
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        TileEntityBearTrap trap = (TileEntityBearTrap)te;
        if(!trap.isOpen()) {
            if(Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
            } else {
                super.harvestBlock(worldIn, player, pos, state, te, stack);
            }
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!state.getValue(BAITED)) {
            ItemStack stack = playerIn.getHeldItem(hand);
            if((stack.getItem() instanceof ItemSeedsTFC || isFood(stack)) && !worldIn.isRemote) {
                if(!playerIn.isCreative()) {
                    stack.shrink(1);
                    if(stack.isEmpty()) {
                        playerIn.inventory.deleteStack(stack);
                    }
                }
                state = state.withProperty(BAITED, Boolean.valueOf(true));
                worldIn.setBlockState(pos, state, 2);
            }
        }
        return true;
    }

    private boolean isFood(ItemStack stack) {
        AnimalFood food = AnimalFood.get(EntityChickenTFC.class);
        return food != null && food.isFood(stack);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(isCapturable(entityIn)) {
            TileEntityBearTrap trap = getTileEntity(worldIn, pos);
            EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
            if(trap.isOpen()) {
                trap.setCapturedEntity(entityLiving);
                entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                trap.setOpen(false);
                state = state.withProperty(CLOSED, Boolean.valueOf(true));
                state = state.withProperty(BAITED, Boolean.valueOf(false));
                worldIn.setBlockState(pos, state, 2);
            } else if(trap.getCapturedEntity() != null && trap.getCapturedEntity().equals(entityLiving)) {
                entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        AxisAlignedBB captureBox = new AxisAlignedBB(pos.getX() - 10.0D, pos.getY() - 5.0D, pos.getZ() - 10.0D, pos.getX() + 10.0D, pos.getY() + 5.0D, pos.getZ() + 10.0D);
        TileEntityBearTrap snare = getTileEntity(worldIn, pos);
        if(snare.isOpen() && worldIn.getEntitiesWithinAABB(EntityPlayer.class, captureBox).isEmpty() && !worldIn.isRemote) {
            for(EntityAnimalTFC animal : worldIn.getEntitiesWithinAABB(EntityAnimalTFC.class, captureBox)) {
                if((isCapturable(animal)) && !(worldIn.getBlockState(animal.getPosition()).getBlock() instanceof BlockSnare)) {
                    snare.setCapturedEntity(animal);
                    snare.setOpen(false);
                    state = state.withProperty(CLOSED, Boolean.valueOf(true));
                    state = state.withProperty(BAITED, Boolean.valueOf(false));
                    worldIn.setBlockState(pos, state, 2);
                    animal.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    return;
                }
            }
            if(state.getValue(BAITED)) {
                if(rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitCaptureChance) {
                    double entitySelection = rand.nextDouble();
                    EntityAnimalTFC animal;
                    if(entitySelection < 0.1) {
                        if(entitySelection < 0.03) {
                            if(entitySelection < 0.01) {
                                animal = new EntityGrouseTFC(worldIn);
                            } else {
                                animal = new EntityQuailTFC(worldIn);
                            }
                        } else {
                            animal = new EntityDuckTFC(worldIn);
                        }
                    } else if(entitySelection < 0.5) {
                        if(entitySelection < 0.3) {
                            animal = new EntityHareTFC(worldIn);
                        } else {
                            animal = new EntityRabbitTFC(worldIn);
                        }
                    } else {
                        animal = new EntityPheasantTFC(worldIn);
                    }
                    animal.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                    worldIn.spawnEntity(animal);
                    snare.setCapturedEntity(animal);
                    snare.setOpen(false);
                    state = state.withProperty(CLOSED, Boolean.valueOf(true));
                    state = state.withProperty(BAITED, Boolean.valueOf(false));
                    worldIn.setBlockState(pos, state, 2);
                } else if(rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitExpireChance) {
                    state = state.withProperty(BAITED, Boolean.valueOf(false));
                    worldIn.setBlockState(pos, state, 2);
                }
            }
        }
    }

    private boolean isCapturable(Entity entityIn) {
        return entityIn instanceof EntityRabbitTFC || entityIn instanceof EntityPheasantTFC || entityIn instanceof EntityDuckTFC || entityIn instanceof EntityChickenTFC || entityIn instanceof EntityTurkeyTFC;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
            TileEntityBearTrap trap = getTileEntity(worldIn, pos);
            if (!trap.isOpen()) {
                if (Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
                } else {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            } else {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }
            worldIn.setBlockToAir(pos);
        }
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TRAP_AABB;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSnare;
    }
}
