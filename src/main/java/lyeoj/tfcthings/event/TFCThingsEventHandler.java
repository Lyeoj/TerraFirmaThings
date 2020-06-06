package lyeoj.tfcthings.event;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.ISharpness;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = TFCThings.MODID)
public class TFCThingsEventHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void applyTooltip(ItemTooltipEvent event) {
        if(event.getItemStack().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = event.getItemStack().getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
            if(capability.getCharges() > 0) {
                event.getToolTip().add("Sharp for " + capability.getCharges() + " uses");
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = event.getPlayer().getHeldItemMainhand().getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
            if(capability.getCharges() > 0) {
                capability.removeCharge();
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if(event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource source = (EntityDamageSource)event.getSource();
            if(source.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)source.getTrueSource();
                ItemStack weapon;
                if(source instanceof EntityDamageSourceIndirect && ((EntityDamageSourceIndirect)source).getImmediateSource() instanceof EntityThrownWeapon) {
                    weapon = ((EntityThrownWeapon)source.getImmediateSource()).getWeapon();
                } else {
                    weapon = player.getHeldItemMainhand();
                }
                if(weapon.hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
                    ISharpness capability = weapon.getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
                    if(capability.getCharges() > 0) {
                        event.getEntityLiving().setHealth(event.getEntityLiving().getHealth() - 2.0f);
                        capability.removeCharge();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        if(event.getEntityPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = event.getEntityPlayer().getHeldItemMainhand().getCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null);
            Item item = event.getEntityPlayer().getHeldItemMainhand().getItem();
            if(capability.getCharges() > 0) {
                if(item.canHarvestBlock(event.getState())) {
                    if(event.getState().getBlock() instanceof BlockLogTFC && !event.getState().getValue(BlockLogTFC.PLACED)) {
                        return;
                    }
                    event.setNewSpeed(event.getNewSpeed() + 4);
                }
            }
        }
    }

}
