package lyeoj.tfcthings.renderer;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.model.ModelPigvil;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPigvil extends RenderLiving<EntityPigvil> {

    private static final ResourceLocation PIG_YOUNG = new ResourceLocation(TFCThings.MODID, "textures/entity/pigvil.png");

    public RenderPigvil(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPigvil(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPigvil entity) {
        return PIG_YOUNG;
    }

}
