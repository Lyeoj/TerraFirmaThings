package lyeoj.tfcthings.renderer;

import lyeoj.tfcthings.model.*;
import lyeoj.tfcthings.tileentity.TileEntityGemDisplay;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class TESRGemDisplay extends TileEntitySpecialRenderer<TileEntityGemDisplay> {

    private static final float[][][] ITEM_LOCATION = new float[4][8][3];

    private static final ArrayList<Vec3d> EXQUISITE_LOCATION = new ArrayList();
    private static final ArrayList<Vec3d> FLAWLESS_LOCATION = new ArrayList();
    private static final ArrayList<Vec3d> NORMAL_LOCATION = new ArrayList();
    private static final ArrayList<Vec3d> FLAWED_LOCATION = new ArrayList();
    private static final ArrayList<Vec3d> CHIPPED_LOCATION = new ArrayList();

    private static final float[] META_TO_ANGLE = new float[]{180.0F, 90.0F, 0.0F, 270.0F};

    private final ModelGemExquisite EXQUISITE_MODEL = new ModelGemExquisite();
    private final ModelGemFlawless FLAWLESS_MODEL = new ModelGemFlawless();
    private final ModelGemNormal NORMAL_MODEL = new ModelGemNormal();
    private final ModelGemFlawed FLAWED_MODEL = new ModelGemFlawed();
    private final ModelGemChipped CHIPPED_MODEL = new ModelGemChipped();

    public TESRGemDisplay() {
    }

    public void render(TileEntityGemDisplay te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        int dir = te.getBlockMetadata();
        float blockScale = 0.5F;
        ArrayList<Vec3d> location = getLocation(te);
        ModelGemBase model = getModel(te);
        for(int i = 0; i < te.getSize(); ++i) {
            ItemStack stack = (ItemStack)te.getItems().get(i);
            ResourceLocation texture = getGemTexture(stack);
            if (!((ItemStack)te.getItems().get(i)).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.disableCull();
                Vec3d pos = location.get(i);
                if(dir == 1 || dir == 2 || dir == 5 || dir == 6) {
                    pos = new Vec3d(1 - pos.x, pos.y, 1 - pos.z);
                }
                GlStateManager.translate(x + (dir % 2 == 0 ? pos.x : pos.z), y + pos.y, z + (dir % 2 == 0 ? pos.z : pos.x));
                GlStateManager.rotate(180, 0.0F, 0.0F, 0.0F);
                GlStateManager.enableRescaleNormal();
                GlStateManager.scale(blockScale, blockScale, blockScale);
                GlStateManager.enableAlpha();
                this.bindTexture(texture);

                model.render(0.125F);

                GlStateManager.popMatrix();
            }
        }

    }

    private ArrayList<Vec3d> getLocation(TileEntityGemDisplay te) {
        switch(Gem.Grade.valueOf(te.getGrade())) {
            case EXQUISITE:
                return EXQUISITE_LOCATION;
            case FLAWLESS:
                return FLAWLESS_LOCATION;
            case NORMAL:
                return NORMAL_LOCATION;
            case FLAWED:
                return FLAWED_LOCATION;
            default:
                return CHIPPED_LOCATION;
        }
    }

    private ModelGemBase getModel(TileEntityGemDisplay te) {
        switch(Gem.Grade.valueOf(te.getGrade())) {
            case EXQUISITE:
                return EXQUISITE_MODEL;
            case FLAWLESS:
                return FLAWLESS_MODEL;
            case NORMAL:
                return NORMAL_MODEL;
            case FLAWED:
                return FLAWED_MODEL;
            default:
                return CHIPPED_MODEL;
        }
    }

    private ResourceLocation getGemTexture(ItemStack gem) {
        if(gem.getItem() instanceof ItemGem) {
            ItemGem gemItem = (ItemGem)gem.getItem();
            String type = gemItem.gem.toString().toLowerCase();
            String grade = Gem.Grade.valueOf(gem.getItemDamage()).toString().toLowerCase();
            return new ResourceLocation("tfcthings:textures/blocks/gem_display/" + grade + "/" + type + ".png");
        }
        return null;
    }

    static {
        EXQUISITE_LOCATION.add(new Vec3d(0.5, 2.5 ,0.5));

        FLAWLESS_LOCATION.add(new Vec3d(0.5, 2.45, 0.25));
        FLAWLESS_LOCATION.add(new Vec3d(0.5, 2.55, 0.75));

        NORMAL_LOCATION.add(new Vec3d(0.25, 2.5, 0.25));
        NORMAL_LOCATION.add(new Vec3d(0.75, 2.5, 0.25));
        NORMAL_LOCATION.add(new Vec3d(0.5, 2.625, 0.775));

        FLAWED_LOCATION.add(new Vec3d(0.75, 2.5, 0.35));
        FLAWED_LOCATION.add(new Vec3d(0.25, 2.5, 0.35));
        FLAWED_LOCATION.add(new Vec3d(0.5, 2.5, 0.15));
        FLAWED_LOCATION.add(new Vec3d(0.5, 2.5, 0.55));
        FLAWED_LOCATION.add(new Vec3d(0.7, 2.625, 0.825));
        FLAWED_LOCATION.add(new Vec3d(0.3, 2.625, 0.825));

        CHIPPED_LOCATION.add(new Vec3d(0.5, 2.5, 0.5));
        CHIPPED_LOCATION.add(new Vec3d(0.75, 2.5, 0.5));
        CHIPPED_LOCATION.add(new Vec3d(0.25, 2.5, 0.5));
        CHIPPED_LOCATION.add(new Vec3d(0.375, 2.5, 0.25));
        CHIPPED_LOCATION.add(new Vec3d(0.625, 2.5, 0.25));
        CHIPPED_LOCATION.add(new Vec3d(0.5, 2.625, 0.8));
        CHIPPED_LOCATION.add(new Vec3d(0.25, 2.625, 0.8));
        CHIPPED_LOCATION.add(new Vec3d(0.75, 2.625, 0.8));

    }

}
