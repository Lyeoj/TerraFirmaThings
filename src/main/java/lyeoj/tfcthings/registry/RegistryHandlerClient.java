package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.main.TFCThings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = TFCThings.MODID, value = Side.CLIENT)
public class RegistryHandlerClient {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for(int i = 0; i < TFCThingsItems.ITEMLIST.length; i++) {
            registerItemModel(TFCThingsItems.ITEMLIST[i]);
        }

        for(int i = 0; i < TFCThingsBlocks.BLOCKLIST.length; i++) {
            registerBlockModel(TFCThingsBlocks.BLOCKLIST[i]);
        }
    }

    private static void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerBlockModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
