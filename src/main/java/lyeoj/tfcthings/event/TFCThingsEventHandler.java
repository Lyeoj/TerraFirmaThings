package lyeoj.tfcthings.event;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.ISharpness;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.items.ItemRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockToolRack;
import net.dries007.tfc.objects.entity.projectile.EntityThrownWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = TFCThings.MODID)
public class TFCThingsEventHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void applyTooltip(ItemTooltipEvent event) {
        if(event.getItemStack().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = getSharpnessCapability(event.getItemStack());
            if(capability != null && capability.getCharges() > 0) {
                event.getToolTip().add("Sharp for " + capability.getCharges() + " uses");
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = getSharpnessCapability(event.getPlayer().getHeldItemMainhand());
            if(capability != null && capability.getCharges() > 0) {
                capability.removeCharge();
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingDamageEvent event) {
        if(event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource source = (EntityDamageSource)event.getSource();
            if(source.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)source.getTrueSource();
                ItemStack weapon;
                if(source instanceof EntityDamageSourceIndirect && source.getImmediateSource() instanceof EntityThrownWeapon) {
                    weapon = ((EntityThrownWeapon)source.getImmediateSource()).getWeapon();
                } else if(source instanceof EntityDamageSourceIndirect && (source.getImmediateSource() instanceof EntityThrownWeapon)) {
                    weapon = ((EntityThrownRopeJavelin)source.getImmediateSource()).getWeapon();
                } else {
                    weapon = player.getHeldItemMainhand();
                }
                if(weapon.hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
                    ISharpness capability = getSharpnessCapability(weapon);
                    if(capability != null && capability.getCharges() > 0 && event.getAmount() > 2.0f) {
                        event.setAmount(event.getAmount() + ConfigTFCThings.Items.WHETSTONE.damageBoost);
                        capability.removeCharge();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        if(event.getEntityPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = getSharpnessCapability(event.getEntityPlayer().getHeldItemMainhand());
            Item item = event.getEntityPlayer().getHeldItemMainhand().getItem();
            if(capability != null && capability.getCharges() > 0) {
                if(item.canHarvestBlock(event.getState())) {
                    if(event.getState().getBlock() instanceof BlockLogTFC && !event.getState().getValue(BlockLogTFC.PLACED)) {
                        return;
                    }
                    event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed);
                }
            }
        }
    }

    @Nullable
    public static ISharpness getSharpnessCapability(ItemStack itemStack) {
        Object capability = itemStack.getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
        if(capability instanceof ISharpness) {
            ISharpness sharpness = (ISharpness)capability;
            return sharpness;
        }
        return null;
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        if(event.getEntityItem().getItem().getItem() instanceof ItemRopeJavelin) {
            ItemRopeJavelin javelin = (ItemRopeJavelin)event.getEntityItem().getItem().getItem();
            javelin.retractJavelin(event.getEntityItem().getItem(), event.getEntity().getEntityWorld());
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getItemStack().getItem() instanceof ItemRopeJavelin) {
            if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockToolRack) {
                ((ItemRopeJavelin)event.getItemStack().getItem()).retractJavelin(event.getItemStack(), event.getWorld());
            }
        }
    }

}
