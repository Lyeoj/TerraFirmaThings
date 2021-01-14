package lyeoj.tfcthings.blocks;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPigvil extends BlockAnvilTFC {

    private final Metal metal;

    public BlockPigvil(Metal metal) {
        super(new Metal(new ResourceLocation(TFCThings.MODID + ":pigvil_" + metal), metal.getTier(), false, 10, 100, 0, null, null));
        if(metal != TFCRegistries.METALS.getValue(DefaultMetals.STEEL)) {
            this.setTranslationKey("pigvil_" + metal);
            this.setRegistryName("pigvil_" + metal);
        } else {
            this.setTranslationKey("pigvil");
            this.setRegistryName("pigvil");
        }
        this.metal = metal;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.EAST));
    }

    public BlockPigvil() {
        super(new Metal(new ResourceLocation(TFCThings.MODID + ":pigvil_purple_steel"), Metal.Tier.TIER_VI, false, 10, 100, 0, null, null));
        this.setTranslationKey("pigvil_purple_steel");
        this.setRegistryName("pigvil_purple_steel");
        this.metal = Metal.RED_STEEL;
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.EAST));
    }


    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        EntityPigvil pigvil = new EntityPigvil(worldIn);
        pigvil.setAnvil(this);
        pigvil.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), state.getValue(AXIS).getHorizontalAngle(), 0);
        worldIn.spawnEntity(pigvil);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta);
    }

    public Metal getMetal() {
        return metal;
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if(this.getTranslationKey().equals("tile.pigvil_black_steel")) {
            return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK_BLACK);
        } else if(this.getTranslationKey().equals("tile.pigvil_blue_steel")) {
            return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK_BLUE);
        } else if(this.getTranslationKey().equals("tile.pigvil_red_steel")) {
            return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK_RED);
        } else if(this.getTranslationKey().equals("tile.pigvil_purple_steel")) {
            return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK_PURPLE);
        } else {
            return new ItemStack(TFCThingsBlocks.PIGVIL_BLOCK);
        }
    }

}
