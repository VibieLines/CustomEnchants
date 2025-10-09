package vibie.customenchants.armor.boots;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EndershiftEnchantment extends Enchantment {
    public EndershiftEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
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
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            for (PlayerEntity player : world.getPlayers()) {
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    checkEndershiftActivation(serverPlayer, world);
                }
            }
        });
    }

    private static void checkEndershiftActivation(ServerPlayerEntity player, World world) {
        if (player.getHealth() > 6.0f) return;

        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getLevel(vibie.customenchants.register.ModEnchantments.ENDERSHIFT, boots);
        if (level <= 0) return;

        if (player.getItemCooldownManager().isCoolingDown(boots.getItem())) return;

        LivingEntity nearestHostile = findNearestHostile(player, world);
        if (nearestHostile == null) return;

        int teleportDistance = 5 * level;
        BlockPos safePos = findRandomSafePosition(player, teleportDistance, world);
        if (safePos == null) return;

        player.teleport((ServerWorld) world, safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5, player.getYaw(), player.getPitch());
        player.getItemCooldownManager().set(boots.getItem(), 2400);
    }

    private static LivingEntity findNearestHostile(PlayerEntity player, World world) {
        Vec3d playerPos = player.getPos();
        double searchRadius = 20.0;

        List<MobEntity> nearbyMobs = world.getEntitiesByClass(
                MobEntity.class,
                new Box(playerPos.add(-searchRadius, -searchRadius, -searchRadius),
                        playerPos.add(searchRadius, searchRadius, searchRadius)),
                mob -> mob.isAlive() && mob.getTarget() == player
        );

        LivingEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (MobEntity mob : nearbyMobs) {
            double distance = playerPos.distanceTo(mob.getPos());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = mob;
            }
        }

        return nearest;
    }

    private static BlockPos findRandomSafePosition(PlayerEntity player, int distance, World world) {
        Random random = new Random();
        Vec3d playerPos = player.getPos();

        for (int i = 0; i < 12; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double xOffset = Math.cos(angle) * distance;
            double zOffset = Math.sin(angle) * distance;

            BlockPos potentialPos = new BlockPos((int)(playerPos.x + xOffset), (int)playerPos.y, (int)(playerPos.z + zOffset));
            BlockPos safeYPos = findSafeYPosition(potentialPos, world);
            if (safeYPos != null && isPositionSafe(safeYPos, world)) {
                return safeYPos;
            }
        }

        return null;
    }

    private static BlockPos findSafeYPosition(BlockPos pos, World world) {
        if (isPositionSafe(pos, world)) {
            return pos;
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos above = pos.up(y);
            if (isPositionSafe(above, world)) {
                return above;
            }
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos below = pos.down(y);
            if (isPositionSafe(below, world)) {
                return below;
            }
        }

        return null;
    }

    private static boolean isPositionSafe(BlockPos pos, World world) {
        return world.isAir(pos) &&
                world.isAir(pos.up()) &&
                !world.isAir(pos.down()) &&
                world.getBlockState(pos.down()).isSolid();
    }
}