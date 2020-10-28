package lyeoj.tfcthings.renderer;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.main.TFCThings;
import lyeoj.tfcthings.model.ModelPigvil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPigvil extends RenderLiving<EntityPigvil> {

    private static final ResourceLocation PIGVIL = new ResourceLocation(TFCThings.MODID, "textures/entity/pigvil.png");
    private static final ResourceLocation PIGVIL_BLACK = new ResourceLocation(TFCThings.MODID, "textures/entity/pigvil_black.png");
    private static final ResourceLocation PIGVIL_RED = new ResourceLocation(TFCThings.MODID, "textures/entity/pigvil_red.png");
    private static final ResourceLocation PIGVIL_BLUE = new ResourceLocation(TFCThings.MODID, "textures/entity/pigvil_blue.png");

    public RenderPigvil(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPigvil(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPigvil entity) {
        Block anvil = entity.getAnvil();
        if(anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK.getRegistryName()) {
            return PIGVIL;
        } else if(anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK_BLACK.getRegistryName()) {
            return PIGVIL_BLACK;
        } else if(anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK_BLUE.getRegistryName()) {
            return PIGVIL_BLUE;
        } else {
            return PIGVIL_RED;
        }
    }

}
