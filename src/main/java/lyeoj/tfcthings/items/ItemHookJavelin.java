package lyeoj.tfcthings.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lyeoj.tfcthings.entity.projectile.EntityThrownHookJavelin;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemHookJavelin extends ItemRopeJavelin implements TFCThingsConfigurableItem {

    public ItemHookJavelin(Metal metal, String name) {
        super(metal, name);
        this.setMaxDamage((int)((double)material.getMaxUses() * 0.3D));
        this.pullStrength = 0.2;
    }

    protected EntityThrownRopeJavelin makeNewJavelin(World worldIn, EntityPlayer player) {
        return new EntityThrownHookJavelin(worldIn, player);
    }

    protected String getNamePrefix() {
        return "hook_javelin";
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(!playerIn.onGround) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if(isThrown(itemstack) && getCapturedEntity(itemstack, worldIn) == null) {
                if(getJavelin(itemstack, worldIn) != null) {
                    EntityThrownHookJavelin javelin = (EntityThrownHookJavelin)getJavelin(itemstack, worldIn);
                    javelin.setLength(javelin.getLength() - 0.5f);
                    if(playerIn.isSneaking()) {
                        dismountHookedPlayer(playerIn);
                        this.retractJavelin(itemstack, worldIn);
                    }
                }
                return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            } else {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
        } else {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
    }

    private void dismountHookedPlayer(EntityPlayer playerIn) {

        double d1 = playerIn.posX;
        double d13 = playerIn.posY;
        double d14 = playerIn.posZ;
        EnumFacing enumfacing1 = playerIn.getAdjustedHorizontalFacing();

        if (enumfacing1 != null)
        {
            EnumFacing enumfacing = enumfacing1.rotateY();
            int[][] aint1 = new int[][] {{0, 1}, {0, -1}, { -1, 1}, { -1, -1}, {1, 1}, {1, -1}, { -1, 0}, {1, 0},
                    {0, 2}, {0, -2}, { -1, 2}, { -1, -2}, {1, 2}, {1, -2}, {2, 2}, {2, -2}, {-2, 2}, {-2, -2},
                    {2, 0}, {2, 1}, {2, -1}, {-2, 0}, {-2, 1}, {-2, -1}};
            double d5 = Math.floor(playerIn.posX) + 0.5D;
            double d6 = Math.floor(playerIn.posZ) + 0.5D;
            double d7 = playerIn.getEntityBoundingBox().maxX - playerIn.getEntityBoundingBox().minX;
            double d8 = playerIn.getEntityBoundingBox().maxZ - playerIn.getEntityBoundingBox().minZ;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(d5 - d7 / 2.0D, playerIn.getEntityBoundingBox().minY, d6 - d8 / 2.0D, d5 + d7 / 2.0D, Math.floor(playerIn.getEntityBoundingBox().minY) + (double)playerIn.height, d6 + d8 / 2.0D);

            for (int[] aint : aint1)
            {
                double d9 = (double)(enumfacing1.getXOffset() * aint[0] + enumfacing.getXOffset() * aint[1]);
                double d10 = (double)(enumfacing1.getZOffset() * aint[0] + enumfacing.getZOffset() * aint[1]);
                double d11 = d5 + d9;
                double d12 = d6 + d10;
                AxisAlignedBB axisalignedbb1 = axisalignedbb.offset(d9, 0.0D, d10);

                if (!playerIn.world.collidesWithAnyBlock(axisalignedbb1))
                {

                    BlockPos blockpos = new BlockPos(d11, playerIn.posY, d12);

                    if (playerIn.world.getBlockState(blockpos).isSideSolid(playerIn.world, blockpos, EnumFacing.UP))
                    {
                        playerIn.setPositionAndUpdate(d11, playerIn.posY + 1.0D, d12);
                        return;
                    }

                    blockpos = new BlockPos(d11, playerIn.posY - 1.0D, d12);

                    if (playerIn.world.getBlockState(blockpos).isSideSolid(playerIn.world, blockpos, EnumFacing.UP) || playerIn.world.getBlockState(blockpos).getMaterial() == Material.WATER)
                    {
                        d1 = d11;
                        d13 = playerIn.posY + 1.0D;
                        d14 = d12;
                    }
                } else if (!playerIn.world.collidesWithAnyBlock(axisalignedbb1.offset(0.0D, 2.0D, 0.0D))) {
                    BlockPos blockPos = new BlockPos(d11, playerIn.posY + 1.0D, d12);
                    if(playerIn.world.getBlockState(blockPos).isSideSolid(playerIn.world, blockPos, EnumFacing.UP)) {
                        playerIn.setPositionAndUpdate(d11, playerIn.posY + 2.0D, d12);
                        return;
                    }
                    blockPos = new BlockPos(d11, playerIn.posY, d12);
                    if(playerIn.world.getBlockState(blockPos).isSideSolid(playerIn.world, blockPos, EnumFacing.UP)) {
                        playerIn.setPositionAndUpdate(d11, playerIn.posY + 1.2D, d12);
                        return;
                    }
                }
            }
        }
        playerIn.setPositionAndUpdate(d1, d13, d14);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND && !isThrown(stack)) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage * 0.4, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableHookJavelins;
    }
}
