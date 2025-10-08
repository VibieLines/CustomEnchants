package vibie.customenchants.weapons.swords;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import vibie.customenchants.register.ModEnchantments;
import vibie.customenchants.weapons.swordandaxes.SharperEnchantment;

public class ExecutionEnchantment extends Enchantment {

    public ExecutionEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 20 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
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
        return !(other instanceof LifestealEnchantment) &&
                !(other instanceof SharperEnchantment) &&
                !(other instanceof VampireEnchantment);
    }

    public static void registerAttackCallback() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity target) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                if (cooldown >= 0.8f) {
                    ItemStack weapon = player.getMainHandStack();
                    int level = EnchantmentHelper.getLevel(ModEnchantments.EXECUTION, weapon);

                    if (level > 0 && canExecute(target, level, world.random)) {
                        target.kill();
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static boolean canExecute(LivingEntity target, int level, Random random) {
        if (target.getHealth() > 6.0f) {
            return false;
        }

        float chance = getExecutionChance(level);
        return random.nextFloat() < chance;
    }

    private static float getExecutionChance(int level) {
        switch (level) {
            case 1: return 0.05f;
            case 2: return 0.07f;
            case 3: return 0.10f;
            default: return 0.05f;
        }
    }
}