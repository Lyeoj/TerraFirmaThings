package lyeoj.tfcthings.items;

import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.damage.IDamageResistance;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.objects.blocks.BlockSnowTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;

public class ItemSnowShoes extends ItemArmor implements IItemSize, IDamageResistance, TFCThingsConfigurableItem {

    private IArmorMaterialTFC materialTFC;
    private static final String STEPS_NBT_KEY = "Steps";
    private double posX;
    private double posZ;

    public ItemSnowShoes(IArmorMaterialTFC materialTFC, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialTFC.getMaterial(), renderIndexIn, equipmentSlotIn);
        this.setTranslationKey("snow_shoes");
        this.setRegistryName("snow_shoes");
        this.materialTFC = materialTFC;
        this.setNoRepair();
    }

    public float getCrushingModifier() {
        return this.materialTFC.getCrushingModifier();
    }

    public float getPiercingModifier() {
        return this.materialTFC.getPiercingModifier();
    }

    public float getSlashingModifier() {
        return this.materialTFC.getSlashingModifier();
    }

    @Nonnull
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.LARGE;
    }

    @Nonnull
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.HEAVY;
    }

    public boolean canStack(@Nonnull ItemStack stack) {
        return false;
    }

    public int getSteps(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if(!stack.getTagCompound().hasKey(STEPS_NBT_KEY)) {
            return 0;
        }
        return stack.getTagCompound().getInteger(STEPS_NBT_KEY);
    }

    public void setSteps(ItemStack stack, int steps) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger(STEPS_NBT_KEY, steps);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if(getSteps(itemStack) > ConfigTFCThings.Items.SNOW_SHOES.damageTicks && !world.isRemote) {
            itemStack.damageItem(1, player);
            setSteps(itemStack, 0);
        }
        if(player.onGround && !player.isRiding() && !player.isCreative()) {
            AxisAlignedBB axisalignedbb = player.getEntityBoundingBox();
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();

            if (player.world.isAreaLoaded(blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1))
            {
                for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); ++i)
                {
                    for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); ++j)
                    {
                        for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); ++k)
                        {
                            blockpos$pooledmutableblockpos2.setPos(i, j, k);
                            IBlockState iblockstate = world.getBlockState(blockpos$pooledmutableblockpos2);
                            if(iblockstate.getBlock() instanceof BlockSnowTFC) {
                                double speedModifier = ConfigTFC.General.MISC.snowMovementModifier + ((1 - ConfigTFC.General.MISC.snowMovementModifier) * ConfigTFCThings.Items.SNOW_SHOES.shoePower);
                                player.motionX /= ConfigTFC.General.MISC.snowMovementModifier;
                                player.motionZ /= ConfigTFC.General.MISC.snowMovementModifier;
                                player.motionX *= speedModifier;
                                player.motionZ *= speedModifier;
                                if(!world.isRemote && ConfigTFCThings.Items.SNOW_SHOES.damageTicks > 0 && (posX != player.posX || posZ != player.posZ)) {
                                    setSteps(itemStack, getSteps(itemStack) + 1);
                                    posX = player.posX;
                                    posZ = player.posZ;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableSnowShoes;
    }
}
