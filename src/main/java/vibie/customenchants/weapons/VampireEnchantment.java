package vibie.customenchants.weapons;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import vibie.customenchants.register.ModEnchantments;

public class VampireEnchantment extends Enchantment {

    public VampireEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 4;
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
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity target) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                ItemStack weapon = player.getMainHandStack();
                int level = EnchantmentHelper.getLevel(ModEnchantments.VAMPIRE, weapon);

                if (level > 0 && cooldown >= 0.9f) {
                    Random random = world.getRandom();
                    if (random.nextFloat() < 0.25f) {
                        float amount = 0.50f * level;
                        world.getServer().execute(() -> {
                            target.hurtTime = 0;
                            target.timeUntilRegen = 0;

                            player.heal(amount);

                            DamageSources damageSources = world.getDamageSources();
                            target.damage(damageSources.magic(), amount);
                        });
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}