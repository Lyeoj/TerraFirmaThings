package lyeoj.tfcthings.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitySlingStoneMetalLight extends EntitySlingStone {

    public EntitySlingStoneMetalLight(World worldIn) {
        super(worldIn);
    }

    public EntitySlingStoneMetalLight(World worldIn, EntityLivingBase throwerIn, int power) {
        super(worldIn, throwerIn, power);
    }

    protected float getGravityVelocity()
    {
        return 0.02F;
    }

}
