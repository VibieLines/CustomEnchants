package vibie.customenchants.boots;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

public class LavaWalkerEnchantment extends Enchantment {

    public LavaWalkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
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

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof FrostWalkerEnchantment) && super.canAccept(other);
    }

    public static void registerTickCallback() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                tick(player);
            }
        });
    }

    private static void tick(ServerPlayerEntity player) {
        World world = player.getWorld();
        ItemStack boots = player.getInventory().getArmorStack(0);
        int level = EnchantmentHelper.getLevel(ModEnchantments.LAVA_WALKER, boots);
        if (level > 0 && !player.isSpectator()) {
            BlockPos playerPos = player.getBlockPos();
            int radius = level + 1;
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.FIRE_RESISTANCE,
                    100,
                    0,
                    false,
                    false,
                    true
            ));
            for (BlockPos pos : BlockPos.iterateOutwards(playerPos, radius, radius, radius)) {
                if (pos.getSquaredDistance(playerPos) <= (radius * radius)) {
                    if (pos.getY() == playerPos.getY() - 1) {
                        BlockState state = world.getBlockState(pos);
                        if (state.getBlock() == Blocks.LAVA) {
                            world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}