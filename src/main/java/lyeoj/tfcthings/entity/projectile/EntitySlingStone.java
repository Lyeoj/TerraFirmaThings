package lyeoj.tfcthings.entity.projectile;

import net.dries007.tfc.api.types.IPredator;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySlingStone extends EntityThrowable {

    private int power;

    public EntitySlingStone(World worldIn) {
        super(worldIn);
    }

    public EntitySlingStone(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        power = 1;
    }

    public EntitySlingStone(World worldIn, EntityLivingBase throwerIn, int power) {
        super(worldIn, throwerIn);
        this.power = power;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            float i = power;

            if (result.entityHit instanceof IPredator)
            {
                i *= 2;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
        }

        if (!this.world.isRemote) {
            if(result.entityHit != null ||
                    world.getBlockState(result.getBlockPos()).getCollisionBoundingBox(world, result.getBlockPos()) != Block.NULL_AABB) {
                this.world.setEntityState(this, (byte)3);
                this.setDead();
            }
        }
    }

}
