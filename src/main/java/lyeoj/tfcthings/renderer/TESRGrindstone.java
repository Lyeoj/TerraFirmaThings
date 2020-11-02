package lyeoj.tfcthings.renderer;

import lyeoj.tfcthings.tileentity.TileEntityGrindstone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TESRGrindstone extends TileEntitySpecialRenderer<TileEntityGrindstone> {
    public TESRGrindstone() {
    }

    public void render(TileEntityGrindstone te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        IItemHandler cap = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
        if (cap != null) {
            ItemStack input = cap.getStackInSlot(TileEntityGrindstone.SLOT_INPUT);
            ItemStack grindstone = cap.getStackInSlot(TileEntityGrindstone.SLOT_GRINDSTONE);
            IBakedModel outputModel;
            int dir = te.getBlockMetadata();
            float angle;
            switch(dir) {
                case 0:
                    angle = 270;
                    break;
                case 1:
                    angle = 180;
                    break;
                case 2:
                    angle = 90;
                    break;
                default:
                    angle = 0;
                    break;
            }
            int rotationTicks;
            if (!grindstone.isEmpty()) {
                rotationTicks = te.getRotationTimer();
                GlStateManager.enableRescaleNormal();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.enableBlend();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
                if (rotationTicks > 0) {
                    int axis = te.getFlowDirection();
                    GlStateManager.rotate(((float)rotationTicks - partialTicks) * (axis % 2 == 0 ? 4.0F : -4.0F), axis <= 2 ? 1.0F : 0.0F, 0.0F, axis <= 2 ? 0.0F : 1.0F);
                }
                GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
                outputModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(grindstone, te.getWorld(), (EntityLivingBase)null);
                outputModel = ForgeHooksClient.handleCameraTransforms(outputModel, ItemCameraTransforms.TransformType.FIXED, false);
                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                Minecraft.getMinecraft().getRenderItem().renderItem(grindstone, outputModel);
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
            }

            if (!input.isEmpty()) {
                GlStateManager.enableRescaleNormal();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.enableBlend();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                Vec3d pos = new Vec3d(0.1, 0.25, 0.5D);
                if(dir == 1 || dir == 2) {
                    pos = new Vec3d(1 - pos.x, pos.y, 1 - pos.z);
                }
                GlStateManager.translate(x + (dir % 2 == 0 ? pos.z : pos.x), y + pos.y, z + (dir % 2 == 0 ? pos.x : pos.z));
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(270 - angle, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(0.65D, 0.65D, 0.65);
                IBakedModel inputModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(input, te.getWorld(), (EntityLivingBase)null);
                inputModel = ForgeHooksClient.handleCameraTransforms(inputModel, ItemCameraTransforms.TransformType.GROUND, false);
                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                Minecraft.getMinecraft().getRenderItem().renderItem(input, inputModel);
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
            }
        }
    }
}
