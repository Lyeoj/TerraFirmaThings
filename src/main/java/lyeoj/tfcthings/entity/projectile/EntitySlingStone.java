package lyeoj.tfcthings.entity.projectile;

import net.dries007.tfc.api.types.IPredator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
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
            int i = power;

            if (result.entityHit instanceof IPredator)
            {
                i *= 3;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
