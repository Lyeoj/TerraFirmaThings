package lyeoj.tfcthings.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelGemFlawless extends ModelBase implements ModelGemBase {
	private final ModelRenderer core;

	public ModelGemFlawless() {
		textureWidth = 48;
		textureHeight = 48;

		core = new ModelRenderer(this);
		core.setRotationPoint(0.0F, 20.0F, 0.0F);
		core.cubeList.add(new ModelBox(core, 24, 0, -4.0F, -1.0F, -2.0F, 8, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 24, 5, -2.0F, -1.0F, -4.0F, 4, 1, 8, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 24, 14, -3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 27, 21, -3.0F, 0.0F, -2.0F, 6, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 28, 26, -2.0F, 0.0F, -3.0F, 4, 1, 6, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 22, 29, -2.0F, 1.0F, -1.0F, 4, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 36, 33, -1.0F, 1.0F, -2.0F, 2, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 24, 10, -1.0F, 2.0F, -1.0F, 2, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 21, 41, -1.0F, -2.0F, -3.0F, 2, 1, 6, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 32, 38, -3.0F, -2.0F, -1.0F, 6, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 32, 41, -2.0F, -2.0F, -2.0F, 4, 1, 4, 0.0F, false));
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