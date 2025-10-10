package vibie.customenchants.armor.leggings;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import vibie.customenchants.register.ModEnchantments;

import java.util.UUID;

public class SwiftEnchantment extends Enchantment {

    private static final UUID SWIFT = UUID.fromString("a3b02c7d-8a4e-4b3a-9c2d-1e5f6a8b9c0d");

    public SwiftEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 20;
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
        ItemStack leggings = player.getInventory().getArmorStack(1);
        int level = EnchantmentHelper.getLevel(ModEnchantments.SWIFT, leggings);
        EntityAttributeInstance movementSpeed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if (movementSpeed == null) return;

        EntityAttributeModifier existingModifier = movementSpeed.getModifier(SWIFT);
        if (existingModifier != null) {
            movementSpeed.removeModifier(SWIFT);
        }

        if (level > 0 && !player.isSpectator()) {
            double speedBonus = 0.03 * level;
            EntityAttributeModifier speedModifier = new EntityAttributeModifier(SWIFT, "Swift", speedBonus, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            movementSpeed.addPersistentModifier(speedModifier);
        }
    }
}