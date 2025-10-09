package vibie.customenchants.tools.pickaxes;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import vibie.customenchants.register.ModEnchantments;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GambleEnchantment extends Enchantment {

    private static final Random random = new Random();
    private static final Map<Block, Block> oreUpgrades = new HashMap<>();

    static {
        oreUpgrades.put(Blocks.COAL_ORE, Blocks.IRON_ORE);
        oreUpgrades.put(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_IRON_ORE);
        oreUpgrades.put(Blocks.IRON_ORE, Blocks.GOLD_ORE);
        oreUpgrades.put(Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE);
        oreUpgrades.put(Blocks.GOLD_ORE, Blocks.LAPIS_ORE);
        oreUpgrades.put(Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_LAPIS_ORE);
        oreUpgrades.put(Blocks.LAPIS_ORE, Blocks.EMERALD_ORE);
        oreUpgrades.put(Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_EMERALD_ORE);
        oreUpgrades.put(Blocks.EMERALD_ORE, Blocks.DIAMOND_ORE);
        oreUpgrades.put(Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
        oreUpgrades.put(Blocks.DIAMOND_ORE, Blocks.ANCIENT_DEBRIS);
        oreUpgrades.put(Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.ANCIENT_DEBRIS);
    }

    public GambleEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxPower(int level) {
        return 60;
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

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof PickaxeItem;
    }

    public static void registerBlockBreakCallback() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world.isClient) return;

            ItemStack tool = player.getMainHandStack();
            int level = EnchantmentHelper.getLevel(ModEnchantments.GAMBLE, tool);

            if (level > 0 && tool.getItem() instanceof PickaxeItem) {
                if (player.getItemCooldownManager().isCoolingDown(tool.getItem())) return;

                if (random.nextFloat() < 0.25f) {
                    player.getItemCooldownManager().set(tool.getItem(), 24000);

                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -2; z <= 2; z++) {
                                BlockPos checkPos = pos.add(x, y, z);
                                Block currentBlock = world.getBlockState(checkPos).getBlock();

                                if (oreUpgrades.containsKey(currentBlock)) {
                                    Block upgradedBlock = oreUpgrades.get(currentBlock);
                                    world.setBlockState(checkPos, upgradedBlock.getDefaultState());
                                }
                            }
                        }
                    }
                } else {
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -2; z <= 2; z++) {
                                BlockPos checkPos = pos.add(x, y, z);
                                Block currentBlock = world.getBlockState(checkPos).getBlock();

                                if (oreUpgrades.containsKey(currentBlock)) {
                                    world.setBlockState(checkPos, Blocks.COAL_ORE.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}