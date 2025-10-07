package vibie.customenchants.armor.boots;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

public class AllInOneWalkerEnchantment extends Enchantment {

    public AllInOneWalkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
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
        return !(other instanceof FrostWalkerEnchantment) && !(other instanceof LavaWalkerEnchantment) && super.canAccept(other);
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
        int level = EnchantmentHelper.getLevel(ModEnchantments.ALL_IN_ONE_WALKER, boots);

        if (level > 0 && !player.isSpectator()) {
            BlockPos playerPos = player.getBlockPos();
            int radius = level + 1;

            // Give fire resistance for lava/magma immunity
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
                        else if (state.getBlock() == Blocks.WATER) {
                            world.setBlockState(pos, Blocks.FROSTED_ICE.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}