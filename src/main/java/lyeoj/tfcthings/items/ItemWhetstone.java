package lyeoj.tfcthings.items;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemWhetstone extends Item implements IItemSize {

    public ItemWhetstone() {
        setTranslationKey("whetstone");
        setRegistryName("whetstone");
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().getItem() instanceof ItemMetalTool) {
//            System.out.println("food");
//            ItemMetalTool tool = (ItemMetalTool)playerIn.getHeldItemOffhand().getItem();

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
