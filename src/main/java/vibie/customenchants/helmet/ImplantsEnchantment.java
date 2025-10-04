package vibie.customenchants.helmet;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import vibie.customenchants.register.ModEnchantments;

public class ImplantsEnchantment extends Enchantment {

    public ImplantsEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinPower(int level) {
        return 10 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 30;
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
            int level = EnchantmentHelper.getLevel(ModEnchantments.IMPLANTS, helmet);

            if (level > 0) {
                float hungerAmount = 1.5f * level;
                player.getHungerManager().add((int) hungerAmount, 0.1f);
            }
        }
    }
}