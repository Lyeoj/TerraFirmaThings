package lyeoj.tfcthings.registry;

import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.items.ItemTFCThingsMold;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

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

        ItemTFCThingsMold item = ItemTFCThingsMold.get("prospectors_hammer_head");
        ModelBakery.registerItemVariants(item, new ModelResourceLocation(item.getRegistryName().toString() + "/empty"));
        ModelBakery.registerItemVariants(item, TFCRegistries.METALS.getValuesCollection()
                .stream()
                .filter(Metal.ItemType.PROPICK_HEAD::hasMold)
                .map(x -> new ModelResourceLocation(item.getRegistryName().toString() + "/" + x.getRegistryName().getPath()))
                .toArray(ModelResourceLocation[]::new));
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
        {
            private final ModelResourceLocation FALLBACK = new ModelResourceLocation(item.getRegistryName().toString() + "/empty");

            @Override
            @Nonnull
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                if (cap instanceof IMoldHandler)
                {
                    Metal metal = ((IMoldHandler) cap).getMetal();
                    if (metal != null)
                    {
                        return new ModelResourceLocation(stack.getItem().getRegistryName() + "/" + metal.getRegistryName().getPath());
                    }
                }
                return FALLBACK;
            }
        });

    }

    private static void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerBlockModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
