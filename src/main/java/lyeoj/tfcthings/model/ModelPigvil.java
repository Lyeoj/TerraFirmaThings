package lyeoj.tfcthings.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class ModelPigvil extends ModelQuadruped {

    private final ModelRenderer tusk1;
    private final ModelRenderer tusk2;
    private final ModelRenderer snout;

    public ModelPigvil() {
        super(6, 0.0F);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, 0.0F);
        this.childYOffset = 4.0F;
        this.tusk1 = new ModelRenderer(this, 32, 0);
        this.tusk1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.tusk1.setRotationPoint(-3.0F, 0.5F, -9.0F);
        this.tusk1.rotateAngleX = 0.2617994F;
        this.tusk2 = new ModelRenderer(this, 32, 0);
        this.tusk2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.tusk2.setRotationPoint(2.0F, 0.5F, -9.0F);
        this.tusk2.rotateAngleX = 0.2617994F;
        this.snout = new ModelRenderer(this, 0, 26);
        this.snout.addBox(-2.0F, 0.0F, -10.0F, 4, 3, 3, 0.0F);
        this.snout.addChild(this.tusk1);
        this.snout.addChild(this.tusk2);
        this.head.addChild(this.snout);
    }

    public void render(@Nonnull Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

        this.tusk1.isHidden = false;
        this.tusk2.isHidden = false;

        GlStateManager.pushMatrix();
        this.head.render(par7);
        this.body.render(par7);
        this.leg1.render(par7);
        this.leg2.render(par7);
        this.leg3.render(par7);
        this.leg4.render(par7);
        GlStateManager.popMatrix();
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.tusk1.isHidden = true;
        this.tusk2.isHidden = true;
        this.head.rotateAngleX = par5 / 57.295776F;
        this.head.rotateAngleY = par4 / 57.295776F;
        this.body.rotateAngleX = 1.5707964F;
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    }

}
