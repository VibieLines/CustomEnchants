package vibie.customenchants.tools.pickaxes;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

public class ExplosiveEnchantment extends Enchantment {

    public ExplosiveEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 25;
    }

    @Override
    public int getMaxPower(int level) {
        return 30;
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

    public static void registerAttackCallback() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient) {
                ItemStack tool = player.getMainHandStack();
                int level = EnchantmentHelper.getLevel(ModEnchantments.EXPLOSIVE, tool);

                if (level > 0 && PickaxeOnly(state, tool)) {
                    float power = level * 2.0f;
                    world.createExplosion(player, pos.getX(), pos.getY(), pos.getZ(), power, World.ExplosionSourceType.MOB);
                }
            }
        });
    }

    private static boolean PickaxeOnly(BlockState state, ItemStack tool) {
        return tool.getItem() instanceof MiningToolItem miningTool &&
                miningTool.isSuitableFor(state);
    }
}