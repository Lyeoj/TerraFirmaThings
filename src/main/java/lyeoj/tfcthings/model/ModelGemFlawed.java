package lyeoj.tfcthings.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelGemFlawed extends ModelBase implements ModelGemBase {
	private final ModelRenderer core;

	public ModelGemFlawed() {
		textureWidth = 16;
		textureHeight = 16;

		core = new ModelRenderer(this);
		core.setRotationPoint(0.0F, 23.0F, 1.0F);
		core.cubeList.add(new ModelBox(core, 0, 13, -2.0F, -1.0F, -2.0F, 4, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 0, 6, -1.0F, -1.0F, -3.0F, 2, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 0, 0, -1.0F, -2.0F, -2.0F, 2, 3, 2, 0.0F, false));
	}

	public void render(float f5) {
		core.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}