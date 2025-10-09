package vibie.customenchants.weapons.swords;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import vibie.customenchants.register.ModEnchantments;

import java.util.Iterator;
import java.util.Map;

public class BlessedEnchantment extends Enchantment {

    public BlessedEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
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

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof AmplifierEnchantment) && super.canAccept(other);
    }

    public static void registerAttackCallback() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity target) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                ItemStack weapon = player.getMainHandStack();
                int level = EnchantmentHelper.getLevel(ModEnchantments.BLESSED, weapon);

                if (level > 0 && cooldown >= 0.65f) {
                    Random random = world.getRandom();
                    if (random.nextFloat() < 0.05f) {
                        world.getServer().execute(() -> {
                            removeBadEffects(player);

                            if (level > 1) {
                                StatusEffectInstance currentRegen = player.getStatusEffect(StatusEffects.REGENERATION);
                                if (currentRegen == null || currentRegen.getAmplifier() < 1) {
                                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1));
                                }
                            }
                        });
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static void removeBadEffects(LivingEntity entity) {
        Map<StatusEffect, StatusEffectInstance> activeEffects = entity.getActiveStatusEffects();
        Iterator<Map.Entry<StatusEffect, StatusEffectInstance>> iterator = activeEffects.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<StatusEffect, StatusEffectInstance> entry = iterator.next();
            StatusEffect effect = entry.getKey();

            if (isBadEffect(effect)) {
                entity.removeStatusEffect(effect);
            }
        }
    }

    private static boolean isBadEffect(StatusEffect effect) {
        return effect == StatusEffects.POISON ||
                effect == StatusEffects.WITHER ||
                effect == StatusEffects.SLOWNESS ||
                effect == StatusEffects.MINING_FATIGUE ||
                effect == StatusEffects.NAUSEA ||
                effect == StatusEffects.BLINDNESS ||
                effect == StatusEffects.HUNGER ||
                effect == StatusEffects.WEAKNESS ||
                effect == StatusEffects.LEVITATION ||
                effect == StatusEffects.UNLUCK ||
                effect == StatusEffects.BAD_OMEN;
    }
}