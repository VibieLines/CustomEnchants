package vibie.customenchants.armor.helmet;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ClarityEnchantment extends Enchantment {

    public ClarityEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinPower(int level) {
        return 15;
    }

    @Override
    public int getMaxPower(int level) {
        return 30;
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
                tick(player);
            }
        });
    }

    private static void tick(ServerPlayerEntity player) {
        if (player.getWorld().getTime() % 20 == 0) {
            ItemStack helmet = player.getInventory().getArmorStack(3);
            int level = EnchantmentHelper.getLevel(vibie.customenchants.register.ModEnchantments.CLARITY, helmet);

            if (level > 0 && player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
                player.removeStatusEffect(StatusEffects.MINING_FATIGUE);
            }
        }
    }
}