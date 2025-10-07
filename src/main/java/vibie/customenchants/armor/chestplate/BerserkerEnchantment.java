package vibie.customenchants.armor.chestplate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import vibie.customenchants.register.ModEnchantments;

public class BerserkerEnchantment extends Enchantment {

    public BerserkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 15;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 1;
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

    public static void registerTickCallback() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                applyBerserkerEffect(player);
            }
        });
    }

    private static void applyBerserkerEffect(ServerPlayerEntity player) {
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        if (EnchantmentHelper.getLevel(ModEnchantments.BERSERKER, chestplate) <= 0) return;

        float health = player.getHealth();
        int strengthLevel = getStrengthLevelForHealth(health);

        if (strengthLevel >= 0) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.STRENGTH, 60, strengthLevel, false, false, true
            ));
        }
    }

    private static int getStrengthLevelForHealth(float health) {
        if (health <= 4.0f) return 2;
        if (health <= 8.0f) return 1;
        if (health <= 14.0f) return 0;
        return -1;
    }
}