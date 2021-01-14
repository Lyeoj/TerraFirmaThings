package lyeoj.tfcthings.items;

import lyeoj.tfcthings.entity.living.EntityPigvil;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IAnimalTFC;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.entity.animal.EntityPigTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nonnull;

public class ItemPigIronCarrot extends ItemTFC implements TFCThingsConfigurableItem {

    private final Metal metal;

    public ItemPigIronCarrot(Metal metal) {
        this.metal = metal;
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

    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        //Debug utility to raise an animal's familiarity
//        if(entity instanceof EntityAnimalTFC) {
//            EntityAnimalTFC animal = (EntityAnimalTFC)entity;
//            animal.setFamiliarity(animal.getFamiliarity() + 0.1f);
//        }
        if(metal == TFCRegistries.METALS.getValue(DefaultMetals.PIG_IRON) && entity instanceof EntityPigTFC) {
            EntityPigTFC piggy = (EntityPigTFC)entity;
            float requiredFamiliarity = (float)ConfigTFCThings.Misc.PIGVIL.familiarityLevel;
            if(piggy.getGender().equals(IAnimalTFC.Gender.MALE) && piggy.getAge() == IAnimalTFC.Age.ADULT && piggy.getFamiliarity() >= requiredFamiliarity) {
                player.world.playSound(player, piggy.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.world.isRemote) {
                    if(!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if(Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        EntityPigvil pigvil = new EntityPigvil(entity.world);
                        pigvil.copyLocationAndAnglesFrom(piggy);
                        entity.world.spawnEntity(pigvil);
                        pigvil.spawnExplosionParticle();
                        piggy.setDead();
                    }
                }
            }
        } else if(entity instanceof EntityPigvil) {
            EntityPigvil pigvil = (EntityPigvil)entity;
            if(pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL)) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if(Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_BLACK);
                        pigvil.spawnExplosionParticle();
                    }
                }
            } else if(pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_BLACK && (metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL) || metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if(Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        if(metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) {
                            pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_BLUE);
                        } else {
                            pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_RED);
                        }
                        pigvil.spawnExplosionParticle();
                    }
                }
            } else if((pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_RED && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) ||
                    (pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_BLUE && metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if(!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if(Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_PURPLE);
                        pigvil.spawnExplosionParticle();
                    }
                }
            }
        }
        return super.itemInteractionForEntity(itemstack, player, entity, hand);
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enablePigvil;
    }
}
