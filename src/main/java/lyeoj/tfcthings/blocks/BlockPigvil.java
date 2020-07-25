package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPigvil extends BlockAnvilTFC {

    public BlockPigvil() {
        super(Metal.STEEL);
        this.setTranslationKey("pigvil");
        this.setRegistryName("pigvil");
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.EAST));
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        EntityPigvil pigvil = new EntityPigvil(worldIn);
        pigvil.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), state.getValue(AXIS).getHorizontalAngle(), 0);
        worldIn.spawnEntity(pigvil);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        IBlockState tempState = worldIn.getBlockState(pos);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta);
    }

    public Metal getMetal() {
        return Metal.STEEL;
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK);
    }

}
