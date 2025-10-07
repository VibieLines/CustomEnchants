package vibie.customenchants.armor.leggings;

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

public class BoltEnchantment extends Enchantment {

    public BoltEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
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

    public static void registerTickCallback() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                tick(player);
            }
        });
    }

    private static void tick(ServerPlayerEntity player) {
        ItemStack leggings = player.getInventory().getArmorStack(1);
        int level = EnchantmentHelper.getLevel(ModEnchantments.BOLT, leggings);

        if (level > 0) {
            int speedLevel = level;
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED,
                    100,
                    speedLevel - 1,
                    false,
                    false,
                    true
            ));
        }
    }
}