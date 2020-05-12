package lyeoj.tfcthings.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Rock - Lyeoj
 * Created using Tabula 7.1.0
 */
public class ModelSlingStone extends ModelBase {
    public ModelRenderer rock2;
    public ModelRenderer rock1;
    public ModelRenderer rock3;

    public ModelSlingStone() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.rock1 = new ModelRenderer(this, 12, 0);
        this.rock1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rock1.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2, 0.0F);
        this.rock3 = new ModelRenderer(this, 20, 0);
        this.rock3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rock3.addBox(-2.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
        this.rock2 = new ModelRenderer(this, 0, 0);
        this.rock2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rock2.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.rock1.render(f5);
        this.rock3.render(f5);
        this.rock2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
