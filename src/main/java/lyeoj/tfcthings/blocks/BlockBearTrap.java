package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.init.TFCThingsDamageSources;
import lyeoj.tfcthings.items.TFCThingsConfigurableItem;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.tileentity.TileEntityBearTrap;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.IPredator;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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

public class BlockBearTrap extends Block implements IItemSize, TFCThingsConfigurableItem {

    protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
    public static final PropertyBool CLOSED = PropertyBool.create("closed");
    public static final PropertyBool BURIED = PropertyBool.create("buried");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockBearTrap() {
        super(Material.IRON);
        this.setTranslationKey("bear_trap");
        this.setRegistryName("bear_trap");
        this.setCreativeTab(CreativeTabsTFC.CT_METAL);
        this.setHardness(10.0F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURIED, Boolean.valueOf(false)).withProperty(CLOSED, Boolean.valueOf(false)));
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

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TRAP_AABB;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, BURIED, CLOSED});
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4)).withProperty(BURIED, meta / 4 % 2 != 0).withProperty(CLOSED, meta / 8 != 0);
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() + ((Boolean)state.getValue(BURIED) ? 4 : 0) + ((Boolean)state.getValue(CLOSED) ? 8 : 0);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
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

        if (block != Blocks.BARRIER) {
            BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
            return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down());
        }
        else {
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
            if(Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
            } else {
                super.harvestBlock(worldIn, player, pos, state, te, stack);
            }
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.getHeldItem(hand).getItem() instanceof ItemSpade ||
                (playerIn.getHeldItem(hand).getItem() instanceof ItemMetalTool &&
                        ((ItemMetalTool)playerIn.getHeldItem(hand).getItem()).getType().equals(Metal.ItemType.SHOVEL))) {
            playerIn.getHeldItem(hand).damageItem(1, playerIn);
            state = state.cycleProperty(BURIED);
            worldIn.setBlockState(pos, state, 2);
            worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        return true;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(entityIn instanceof EntityLivingBase) {
            TileEntityBearTrap trap = getTileEntity(worldIn, pos);
            EntityLivingBase entityLiving = (EntityLivingBase)entityIn;
            if(trap.isOpen()) {
                int debuffDuration = ConfigTFCThings.Items.BEAR_TRAP.debuffDuration;
                double healthCut = ConfigTFCThings.Items.BEAR_TRAP.healthCut;
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, debuffDuration));
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, debuffDuration));
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, debuffDuration));
                if(ConfigTFCThings.Items.BEAR_TRAP.fixedDamage > 0) {
                    entityLiving.attackEntityFrom(TFCThingsDamageSources.BEAR_TRAP, (float)ConfigTFCThings.Items.BEAR_TRAP.fixedDamage);
                } else if(healthCut > 0) {
                    entityLiving.attackEntityFrom(TFCThingsDamageSources.BEAR_TRAP, entityLiving.getHealth() / (float)healthCut);
                }
                trap.setCapturedEntity(entityLiving);
                entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                trap.setOpen(false);
                state = state.withProperty(CLOSED, Boolean.valueOf(true));
                worldIn.setBlockState(pos, state, 2);
                entityLiving.playSound(SoundEvents.ENTITY_ITEM_BREAK, 2.0F, 0.4F);
            } else if(trap.getCapturedEntity() != null && trap.getCapturedEntity().equals(entityLiving)) {
                entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                if(entityLiving instanceof IPredator && Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakoutChance) {
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
                    if(Math.random() > 2 * ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
                        this.dropBlockAsItem(worldIn, pos, state, 0);
                    }
                    worldIn.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), worldIn.isRemote ? 11 : 3);
                }
                if(entityLiving.isDead) {
                    trap.setCapturedEntity(null);
                }
            }
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
            TileEntityBearTrap trap = getTileEntity(worldIn, pos);
            if (!trap.isOpen()) {
                if (Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
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
        return Weight.HEAVY;
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableBearTrap;
    }
}
