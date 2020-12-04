package lyeoj.tfcthings.entity.living;

import com.google.common.collect.Sets;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsDamageSources;
import lyeoj.tfcthings.init.TFCThingsItems;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

public class EntityPigvil extends EntityCreature {

    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemIngot.get(Metal.PIG_IRON, Metal.ItemType.INGOT), TFCThingsItems.ITEM_PIG_IRON_CARROT, TFCThingsItems.ITEM_BLACK_STEEL_CARROT, TFCThingsItems.ITEM_RED_STEEL_CARROT, TFCThingsItems.ITEM_BLUE_STEEL_CARROT);

    private static final DataParameter<String> ANVIL_TYPE = EntityDataManager.<String>createKey(EntityPigvil.class, DataSerializers.STRING);

    public EntityPigvil(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 0.9F);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ANVIL_TYPE, TFCThingsBlocks.PIGVIL_BLOCK.getRegistryName().toString());
    }

    public Block getAnvil() {
        return Block.getBlockFromName(this.dataManager.get(ANVIL_TYPE));
    }

    public void setAnvil(Block anvil) {
        this.dataManager.set(ANVIL_TYPE, anvil.getRegistryName().toString());
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

    protected SoundEvent getFallSound(int heightIn) {
        return SoundEvents.BLOCK_ANVIL_LAND;
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setString("anvil", getAnvil().getRegistryName().toString());
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        setAnvil(Block.getBlockFromName(compound.getString("anvil")));
        super.readEntityFromNBT(compound);
    }

    public boolean canDespawn() {
        return false;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(player.isSneaking()) {
            return super.processInteract(player, hand);
        }
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        if (this.world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos)) {
            this.world.playSound(player, blockpos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.world.setBlockState(blockpos, getAnvil().getDefaultState().withProperty(BlockAnvilTFC.AXIS, this.getAdjustedHorizontalFacing()));
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

    public void onCollideWithPlayer(EntityPlayer playerIn) {
        if(this.fallDistance > 3 && this.posY > playerIn.posY) {
            playerIn.attackEntityFrom(TFCThingsDamageSources.PIGVIL, fallDistance * 3.0f);
        }
    }


}
