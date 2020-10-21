package lyeoj.tfcthings.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelGemNormal extends ModelBase implements ModelGemBase {
	private final ModelRenderer core;

	public ModelGemNormal() {
		textureWidth = 32;
		textureHeight = 32;

		core = new ModelRenderer(this);
		core.setRotationPoint(0.0F, 22.0F, 0.0F);
		core.cubeList.add(new ModelBox(core, 0, 25, -1.0F, -1.0F, -3.0F, 2, 1, 6, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 16, 24, -3.0F, -1.0F, -1.0F, 6, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 16, 27, -2.0F, -1.0F, -2.0F, 4, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 0, 22, -2.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 20, 19, -1.0F, 0.0F, -2.0F, 2, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 12, 21, -1.0F, 1.0F, -1.0F, 2, 1, 2, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 0, 17, -1.0F, -2.0F, -2.0F, 2, 1, 4, 0.0F, false));
		core.cubeList.add(new ModelBox(core, 12, 18, -2.0F, -2.0F, -1.0F, 4, 1, 2, 0.0F, false));
	}

	@Override
	public void render(float f5) {
		core.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}