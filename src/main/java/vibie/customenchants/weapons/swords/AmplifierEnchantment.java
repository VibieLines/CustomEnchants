package vibie.customenchants.weapons.swords;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import vibie.customenchants.register.ModEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AmplifierEnchantment extends Enchantment {

    public AmplifierEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 20 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 60;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }

    public static void registerAttackCallback() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity target) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                ItemStack weapon = player.getMainHandStack();
                int level = EnchantmentHelper.getLevel(ModEnchantments.AMPLIFIER, weapon);

                if (level > 0 && cooldown >= 0.9f) {
                    Random random = world.getRandom();
                    if (random.nextFloat() < 0.15f) {
                        world.getServer().execute(() -> {
                            amplifyAllEffects(player, level, random);
                        });
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
//reminder to redo this in the future
    private static void amplifyAllEffects(LivingEntity target, int enchantLevel, Random random) {
        Map<StatusEffect, StatusEffectInstance> activeEffects = target.getActiveStatusEffects();

        if (activeEffects.isEmpty()) return;

        List<StatusEffectInstance> newEffects = new ArrayList<>();

        for (Map.Entry<StatusEffect, StatusEffectInstance> entry : activeEffects.entrySet()) {
            StatusEffect effect = entry.getKey();
            StatusEffectInstance currentInstance = entry.getValue();

            if (currentInstance != null) {
                boolean success = random.nextBoolean();

                int currentAmplifier = currentInstance.getAmplifier();
                int currentDuration = currentInstance.getDuration();
                int newAmplifier = currentAmplifier;
                int newDuration = currentDuration;

                if (effect.getTranslationKey().contains("resistance")) {
                    newAmplifier = currentAmplifier;
                } else {
                    if (success) {
                        if (currentAmplifier < 4) {
                            newAmplifier = currentAmplifier + 1;
                        }
                    } else {
                        if (currentAmplifier > 0) {
                            newAmplifier = currentAmplifier - 1;
                        }
                    }
                }

                if (success) {
                    newDuration = Math.min(currentDuration + (100 * enchantLevel), 12000);
                } else {
                    newDuration = Math.max(currentDuration - (50 * enchantLevel), 100);
                }

                StatusEffectInstance newInstance = new StatusEffectInstance(
                        effect,
                        newDuration,
                        newAmplifier,
                        currentInstance.isAmbient(),
                        currentInstance.shouldShowParticles(),
                        currentInstance.shouldShowIcon()
                );
                newEffects.add(newInstance);
            }
        }
        for (StatusEffectInstance newInstance : newEffects) {
            target.removeStatusEffect(newInstance.getEffectType());
            target.addStatusEffect(newInstance);
        }
    }
}