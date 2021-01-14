package lyeoj.tfcthings.event;

import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.ISharpness;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.items.ItemRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import lyeoj.tfcthings.main.TFCThings;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockToolRack;
import net.dries007.tfc.objects.entity.animal.EntitySheepTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownWeapon;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
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
                TextFormatting color = capability.getCharges() > 64 ? capability.getCharges() > 256 ? TextFormatting.DARK_PURPLE : TextFormatting.BLUE : TextFormatting.DARK_GREEN;
                event.getToolTip().add(I18n.format("tfcthings.tooltip.sharpness", color, "" + capability.getCharges()));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = getSharpnessCapability(event.getPlayer().getHeldItemMainhand());
            if(capability != null && capability.getCharges() > 0) {
                capability.removeCharge();
                ItemStack stack = event.getPlayer().getHeldItemMainhand();
                if(capability.getCharges() > 256) {
                    if(Math.random() < 0.2 && stack.getItemDamage() > 0) {
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
                } else if(capability.getCharges() > 64 && stack.getItemDamage() > 0) {
                    if(Math.random() < 0.1) {
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
                }
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
                    if(capability != null && event.getAmount() > 2.0f) {
                        if(capability.getCharges() > 256) {
                            event.setAmount(event.getAmount() + (ConfigTFCThings.Items.WHETSTONE.damageBoost * 3));
                            capability.removeCharge();
                        } else if(capability.getCharges() > 64) {
                            event.setAmount(event.getAmount() + (ConfigTFCThings.Items.WHETSTONE.damageBoost * 2));
                            capability.removeCharge();
                        } else if(capability.getCharges() > 0) {
                            event.setAmount(event.getAmount() + ConfigTFCThings.Items.WHETSTONE.damageBoost);
                            capability.removeCharge();
                        }
                        if(capability.getCharges() > 256) {
                            if(Math.random() < 0.2 && weapon.getItemDamage() > 0) {
                                weapon.setItemDamage(weapon.getItemDamage() - 1);
                            }
                        } else if(capability.getCharges() > 64) {
                            if(Math.random() < 0.1 && weapon.getItemDamage() > 0) {
                                weapon.setItemDamage(weapon.getItemDamage() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        if(event.getEntityPlayer().getHeldItemMainhand().hasCapability(CapabilitySharpness.SHARPNESS_CAPABILITY, null)) {
            ISharpness capability = getSharpnessCapability(event.getEntityPlayer().getHeldItemMainhand());
            if(capability != null) {
                if(shouldBoostSpeed(event.getEntityPlayer().getHeldItemMainhand(), event.getState())) {
                    if(event.getState().getBlock() instanceof BlockLogTFC && !event.getState().getValue(BlockLogTFC.PLACED)) {
                        return;
                    }
                    if(capability.getCharges() > 256) {
                        event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed + 4);
                    } else if(capability.getCharges() > 64) {
                        event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed + 2);
                    } else if(capability.getCharges() > 0) {
                        event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed);
                    }
                }
            }
        }
    }

    private static boolean shouldBoostSpeed(ItemStack stack, IBlockState state) {
        if(stack.getItem().canHarvestBlock(state)) return true;
        for (String type : stack.getItem().getToolClasses(stack)) {
            if(state.getBlock().isToolEffective(type, state)) return true;
        }
        return false;
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
    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
        if(event.getItemStack().getItem() instanceof ItemRopeJavelin) {
            if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockToolRack) {
                ((ItemRopeJavelin)event.getItemStack().getItem()).retractJavelin(event.getItemStack(), event.getWorld());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() instanceof EntitySheepTFC) {
            EntitySheepTFC sheep = (EntitySheepTFC)event.getTarget();
            if((OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "shears") || OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "knife"))
                    && sheep.hasWool() && sheep.getFamiliarity() == 1.0F ) {
                if(!sheep.world.isRemote) {
                    ItemStack woolStack = new ItemStack(ItemsTFC.WOOL, 1);
                    Helpers.spawnItemStack(sheep.world, new BlockPos(sheep.posX, sheep.posY, sheep.posZ), woolStack);
                }
            }
        }
//        if(event.getTarget() instanceof EntityCowTFC) {
//            EntityCowTFC cow = (EntityCowTFC) event.getTarget();
//            if(cow.getFamiliarity() == 1.0F && cow.isReadyForAnimalProduct()) {
//                event.setCanceled(true);
//                ItemStack itemstack = event.getEntityPlayer().getHeldItem(event.getHand());
//                FluidActionResult fillResult = FluidUtil.tryFillContainer(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), 1000, event.getEntityPlayer(), false);
//                if (!fillResult.isSuccess()) {
//                    return;
//                }
//                event.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
//                event.getEntityPlayer().setHeldItem(event.getHand(), FluidUtil.tryFillContainerAndStow(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), new PlayerInvWrapper(event.getEntityPlayer().inventory), 1000, (EntityPlayer)null, true).getResult());
//            }
//        }
    }



}
