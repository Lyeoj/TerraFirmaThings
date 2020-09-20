package lyeoj.tfcthings.renderer;

import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.items.ItemRopeJavelin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

public class RenderThrownRopeJavelin extends Render<EntityThrownRopeJavelin> {

    private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

    public RenderThrownRopeJavelin(RenderManager renderManager) {
        super(renderManager);
    }

    public void doRender(@Nonnull EntityThrownRopeJavelin entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        this.doRenderTransformations(entity, partialTicks);
        this.bindTexture(this.getEntityTexture(entity));
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        ItemStack weapon = entity.getWeapon();
        if (!weapon.isEmpty()) {
            this.itemRenderer.renderItem(weapon, ItemCameraTransforms.TransformType.GROUND);
        }

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        this.renderLeash(entity, x, y, z, entityYaw, partialTicks);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityThrownRopeJavelin entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    protected void doRenderTransformations(EntityThrownRopeJavelin entity, float partialTicks) {
        GlStateManager.translate(0.0D, 0.4D, 0.0D);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 145.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(2.0D, 2.0D, 1.0D);
    }

    public boolean shouldRender(EntityThrownRopeJavelin javelin, ICamera camera, double camX, double camY, double camZ) {
        if (super.shouldRender(javelin, camera, camX, camY, camZ)) {
            return true;
        }
        else {
            Entity entity = javelin.getThrower();
            if(entity != null) {
                return camera.isBoundingBoxInFrustum(entity.getRenderBoundingBox());
            }
            return false;
        }
    }

    protected void renderLeash(EntityThrownRopeJavelin javelin, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, -1.0D, 0.0D);
        if(javelin.getThrower() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)javelin.getThrower();

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            int k = entityplayer.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
            ItemStack itemstack = entityplayer.getHeldItemMainhand();

            if (!(itemstack.getItem() instanceof ItemRopeJavelin))
            {
                k = -k;
            }

            float f7 = entityplayer.getSwingProgress(partialTicks);
            float f8 = MathHelper.sin(MathHelper.sqrt(f7) * (float)Math.PI);
            float f9 = (entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * partialTicks) * 0.017453292F;
            double d0 = (double)MathHelper.sin(f9);
            double d1 = (double)MathHelper.cos(f9);
            double d2 = (double)k * 0.35D;
            double d4;
            double d5;
            double d6;
            double d7;

            if ((this.renderManager.options == null || this.renderManager.options.thirdPersonView <= 0) && entityplayer == Minecraft.getMinecraft().player)
            {
                float f10 = this.renderManager.options.fovSetting;
                f10 = f10 / 100.0F;
                Vec3d vec3d = new Vec3d((double)k * -0.36D * (double)f10, -0.045D * (double)f10, 0.4D);
                vec3d = vec3d.rotatePitch(-(entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * partialTicks) * 0.017453292F);
                vec3d = vec3d.rotateYaw(-(entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * partialTicks) * 0.017453292F);
                vec3d = vec3d.rotateYaw(f8 * 0.5F);
                vec3d = vec3d.rotatePitch(-f8 * 0.7F);
                d4 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)partialTicks + vec3d.x;
                d5 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)partialTicks + vec3d.y;
                d6 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)partialTicks + vec3d.z;
                d7 = (double)entityplayer.getEyeHeight();
            }
            else
            {
                d4 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)partialTicks - d1 * d2 - d0 * 0.8D;
                d5 = entityplayer.prevPosY + (double)entityplayer.getEyeHeight() + (entityplayer.posY - entityplayer.prevPosY) * (double)partialTicks - 0.45D;
                d6 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)partialTicks - d0 * d2 + d1 * 0.8D;
                d7 = entityplayer.isSneaking() ? -0.1875D : 0.0D;
            }

            double d13 = javelin.prevPosX + (javelin.posX - javelin.prevPosX) * (double)partialTicks;
            double d8 = javelin.prevPosY + (javelin.posY - javelin.prevPosY) * (double)partialTicks + 0.17D;
            double d9 = javelin.prevPosZ + (javelin.posZ - javelin.prevPosZ) * (double)partialTicks;
            double d10 = (double)((float)(d4 - d13));
            double d11 = (double)((float)(d5 - d8)) + d7 + 1.0;
            double d12 = (double)((float)(d6 - d9));
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for (int j = 0; j <= 24; ++j)
            {
                float f = 0.5F;
                float f1 = 0.4F;
                float f2 = 0.3F;

                if (j % 2 == 0)
                {
                    f *= 0.7F;
                    f1 *= 0.7F;
                    f2 *= 0.7F;
                }

                float f3 = (float)j / 24.0F;
                bufferbuilder.pos(x + d10 * (double)f3 + 0.0D, y + d11 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F), z + d12 * (double)f3).color(f, f1, f2, 1.0F).endVertex();
                bufferbuilder.pos(x + d10 * (double)f3 + 0.025D, y + d11 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F) + 0.025D, z + d12 * (double)f3).color(f, f1, f2, 1.0F).endVertex();
            }

            tessellator.draw();
            bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for (int l = 0; l <= 24; ++l)
            {
                float f4 = 0.5F;
                float f5 = 0.4F;
                float f6 = 0.3F;

                if (l % 2 == 0)
                {
                    f4 *= 0.7F;
                    f5 *= 0.7F;
                    f6 *= 0.7F;
                }

                float f7_1 = (float)l / 24.0F;
                bufferbuilder.pos(x + d10 * (double)f7_1 + 0.0D, y + d11 * (double)(f7_1 * f7_1 + f7_1) * 0.5D + (double)((24.0F - (float)l) / 18.0F + 0.125F) + 0.025D, z + d12 * (double)f7_1).color(f4, f5, f6, 1.0F).endVertex();
                bufferbuilder.pos(x + d10 * (double)f7_1 + 0.025D, y + d11 * (double)(f7_1 * f7_1 + f7_1) * 0.5D + (double)((24.0F - (float)l) / 18.0F + 0.125F), z + d12 * (double)f7_1 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
            }

            tessellator.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();

        }

    }

}
