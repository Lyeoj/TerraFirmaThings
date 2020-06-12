package lyeoj.tfcthings.entity.projectile;

import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityThrownRopeJavelin extends EntityThrownJavelin {
    public EntityThrownRopeJavelin(World world) {
        super(world);
    }

    public EntityThrownRopeJavelin(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityThrownRopeJavelin(World world, EntityLivingBase shooter) {
        super(world, shooter);
    }


}
