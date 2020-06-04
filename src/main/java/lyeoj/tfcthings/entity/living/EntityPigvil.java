package lyeoj.tfcthings.entity.living;

import com.google.common.collect.Sets;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsItems;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

public class EntityPigvil extends EntityCreature {

    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemIngot.get(Metal.PIG_IRON, Metal.ItemType.INGOT), TFCThingsItems.ITEM_PIG_IRON_CARROT);

    public EntityPigvil(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 0.9F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.equals(DamageSource.OUT_OF_WORLD)|| source.equals(DamageSource.LAVA) || source.equals(DamageSource.IN_FIRE) || source.equals((DamageSource.DROWN))) {
            return super.attackEntityFrom(source, amount);
        }
        return false;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        if (this.world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos)) {
            this.world.playSound(player, blockpos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.world.setBlockState(blockpos, TFCThingsBlocks.PIGVIL_BLOCK.getDefaultState().withProperty(BlockAnvilTFC.AXIS, this.getAdjustedHorizontalFacing()));
            this.setDead();
            return true;
        }
        return super.processInteract(player, hand);
    }

    public EnumFacing getAdjustedHorizontalFacing() {
        EnumFacing facing = this.getHorizontalFacing();
        switch(facing) {
            case WEST:
                return EnumFacing.SOUTH;
            case NORTH:
                return EnumFacing.WEST;
            case EAST:
                return EnumFacing.NORTH;
            default:
                return EnumFacing.EAST;
        }
    }
}
