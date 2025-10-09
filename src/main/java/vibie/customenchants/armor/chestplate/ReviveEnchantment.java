package vibie.customenchants.armor.chestplate;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import vibie.customenchants.register.ModEnchantments;

import java.util.HashMap;
import java.util.Map;

public class ReviveEnchantment extends Enchantment {

    public ReviveEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof HeartsEnchantment) && super.canAccept(other);
    }

    public static void registerDamageCallback() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {
                return checkRevive(player, source, amount);
            }
            return true;
        });
    }
    private static boolean checkRevive(ServerPlayerEntity player, DamageSource source, float amount) {
        if (player.getHealth() > amount) return true;

        ItemStack chestplate = player.getInventory().getArmorStack(2);
        if (chestplate.isEmpty()) return true;

        int reviveLevel = EnchantmentHelper.getLevel(ModEnchantments.REVIVE, chestplate);
        if (reviveLevel <= 0) return true;

        if (activateRevive(player, chestplate)) {
            return false;
        }
        return true;
    }

    private static boolean activateRevive(ServerPlayerEntity player, ItemStack chestplate) {
        player.setHealth(4.0f);
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 3));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 800, 1));

        player.playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
        player.getServerWorld().sendEntityStatus(player, (byte)35);

        halveDurability(chestplate);
        removeReviveEnchantment(chestplate);
        return true;
    }

    private static void halveDurability(ItemStack chestplate) {
        if (chestplate.getDamage() > 1) {
            int currentDamage = chestplate.getDamage();
            int maxDamage = chestplate.getMaxDamage();
            int newDamage = Math.min(maxDamage - 1, currentDamage + (maxDamage - currentDamage) / 2);
            chestplate.setDamage(newDamage);
        }
    }

    private static void removeReviveEnchantment(ItemStack chestplate) {
        Map<Enchantment, Integer> enchantments = new HashMap<>(EnchantmentHelper.get(chestplate));
        enchantments.remove(ModEnchantments.REVIVE);
        EnchantmentHelper.set(enchantments, chestplate);
    }
}