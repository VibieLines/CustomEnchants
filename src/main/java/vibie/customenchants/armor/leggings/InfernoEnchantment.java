package vibie.customenchants.armor.leggings;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

import java.util.List;

public class InfernoEnchantment extends Enchantment {

    public InfernoEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 10;
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
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                tick(player);
            }
        });
    }

    private static void tick(ServerPlayerEntity player) {
        ItemStack leggings = player.getInventory().getArmorStack(1);
        int level = EnchantmentHelper.getLevel(ModEnchantments.INFERNO, leggings);

        if (level > 0) {
            float radius = getRadius(level);
            World world = player.getWorld();
            Box area = new Box(
                    player.getX() - radius, player.getY() - 1, player.getZ() - radius,
                    player.getX() + radius, player.getY() + 2, player.getZ() + radius
            );

            List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                if (entity != player && entity.isAlive()) {
                    entity.setFireTicks(140);
                }
            }
        }
    }

    private static float getRadius(int level) {
        switch (level) {
            case 1: return 1.0f;
            case 2: return 2.0f;
            case 3: return 2.5f;
            default: return 1.0f;
        }
    }
}