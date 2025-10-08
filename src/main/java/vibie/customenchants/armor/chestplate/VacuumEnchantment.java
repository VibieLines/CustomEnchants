package vibie.customenchants.armor.chestplate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

public class VacuumEnchantment extends Enchantment {

    public VacuumEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 30;
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
        if (player.getWorld().getTime() % 3 != 0) return;

        ItemStack chestplate = player.getInventory().getArmorStack(2);
        int level = EnchantmentHelper.getLevel(ModEnchantments.VACUUM, chestplate);

        if (level > 0) {
            pullItemsToPlayer(player, level);
        }
    }

    private static void pullItemsToPlayer(ServerPlayerEntity player, int level) {
        World world = player.getWorld();
        double radius = getRadius(level);
        Box area = new Box(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        for (ItemEntity itemEntity : world.getEntitiesByClass(ItemEntity.class, area, item -> true)) {
            if (itemEntity.isRemoved() || !itemEntity.isAlive()) continue;

            Vec3d playerPos = player.getPos();
            Vec3d itemPos = itemEntity.getPos();
            double distance = playerPos.distanceTo(itemPos);
            if (distance < 0.5) continue;

            Vec3d direction = playerPos.subtract(itemPos).normalize();
            double baseSpeed = 0.1 + (level * 0.03);
            double distanceFactor = Math.min(distance / radius, 1.0);
            double speed = baseSpeed * distanceFactor;

            Vec3d velocity = direction.multiply(speed);

            Vec3d currentVel = itemEntity.getVelocity();
            Vec3d dampenedVel = currentVel.multiply(0.7);

            itemEntity.setVelocity(dampenedVel.x + velocity.x, dampenedVel.y + velocity.y + 0.05, dampenedVel.z + velocity.z);

            itemEntity.velocityModified = true;
        }
    }

    private static double getRadius(int level) {
        switch (level) {
            case 1: return 2.0;
            case 2: return 3.0;
            case 3: return 4.0;
            default: return 2.0;
        }
    }
}