package lyeoj.tfcthings.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelGemExquisite extends ModelBase implements ModelGemBase {
	private final ModelRenderer Core;
	private final ModelRenderer Side;
	private final ModelRenderer Side2;
	private final ModelRenderer Side3;
	private final ModelRenderer Side4;

	public ModelGemExquisite() {
		textureWidth = 64;
		textureHeight = 64;

		Core = new ModelRenderer(this);
		Core.setRotationPoint(0.0F, 16.5F, 0.0F);
		Core.cubeList.add(new ModelBox(Core, 0, 43, -4.0F, -0.5F, -4.0F, 8, 1, 8, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 32, 47, -6.0F, -0.5F, -2.0F, 12, 1, 4, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 26, 34, -2.0F, -0.5F, -6.0F, 4, 1, 12, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 24, 40, -3.0F, -0.5F, -5.0F, 6, 1, 1, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 24, 43, -3.0F, -0.5F, 4.0F, 6, 1, 1, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 36, 4.0F, -0.5F, -3.0F, 1, 1, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 8, 35, -5.0F, -0.5F, -3.0F, 1, 1, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 8, -4.0F, 0.5F, -3.0F, 1, 2, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 24, 0, 3.0F, 0.5F, -3.0F, 1, 2, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 24, 37, -3.0F, 0.5F, 3.0F, 6, 2, 1, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 22, 33, -3.0F, 0.5F, -4.0F, 6, 2, 1, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 40, 56, -3.0F, 2.5F, -3.0F, 6, 2, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 48, 40, -2.0F, 4.5F, -2.0F, 4, 2, 4, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 28, -5.0F, 0.5F, -2.0F, 10, 1, 4, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 36, 23, -2.0F, 0.5F, -5.0F, 4, 1, 10, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 23, -4.0F, 2.5F, -2.0F, 8, 1, 4, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 16, -2.0F, 4.5F, -3.0F, 4, 1, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 24, 26, -3.0F, 4.5F, -2.0F, 6, 1, 4, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 20, 17, -2.0F, 2.5F, -4.0F, 4, 1, 8, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 36, 0, -3.0F, -1.5F, -4.0F, 6, 1, 8, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 16, 10, -4.0F, -1.5F, -3.0F, 8, 1, 6, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 36, 12, -2.0F, -1.5F, -5.0F, 4, 1, 10, 0.0F, false));
		Core.cubeList.add(new ModelBox(Core, 0, 0, -5.0F, -1.5F, -2.0F, 10, 1, 4, 0.0F, false));

		Side = new ModelRenderer(this);
		Side.setRotationPoint(0.0F, 20.0F, 3.5F);
		setRotationAngle(Side, -0.5236F, 0.0F, 0.0F);
		Side.cubeList.add(new ModelBox(Side, 10, 55, -2.0F, -4.0F, -0.5F, 4, 8, 1, 0.0F, false));

		Side2 = new ModelRenderer(this);
		Side2.setRotationPoint(0.0F, 20.0F, -3.5F);
		setRotationAngle(Side2, 0.5236F, 0.0F, 0.0F);
		Side2.cubeList.add(new ModelBox(Side2, 0, 55, -2.0F, -4.0F, -0.5F, 4, 8, 1, 0.0F, false));

		Side3 = new ModelRenderer(this);
		Side3.setRotationPoint(3.5F, 20.0F, 0.0F);
		setRotationAngle(Side3, 0.0F, 0.0F, 0.5236F);
		Side3.cubeList.add(new ModelBox(Side3, 20, 52, -0.5F, -4.0F, -2.0F, 1, 8, 4, 0.0F, false));

		Side4 = new ModelRenderer(this);
		Side4.setRotationPoint(-3.5F, 20.0F, 0.0F);
		setRotationAngle(Side4, 0.0F, 0.0F, -0.5236F);
		Side4.cubeList.add(new ModelBox(Side4, 30, 52, -0.5F, -4.0F, -2.0F, 1, 8, 4, 0.0F, false));
	}

	public void render(float f5) {
		Core.render(f5);
		Side.render(f5);
		Side2.render(f5);
		Side3.render(f5);
		Side4.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}