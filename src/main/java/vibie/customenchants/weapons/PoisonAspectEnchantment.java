package vibie.customenchants.weapons;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import vibie.customenchants.register.ModEnchantments;
//bit messy will clean up in the future if i remember
public class PoisonAspectEnchantment extends Enchantment {

    public PoisonAspectEnchantment() {
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
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity target) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                if (cooldown >= 0.70f) {
                    ItemStack weapon = player.getMainHandStack();
                    int level = EnchantmentHelper.getLevel(ModEnchantments.POISON_ASPECT, weapon);

                    if (level > 0) {
                        int duration = 30 * level;
                        int amplifier = level - 1;
                        world.getServer().execute(() -> {
                            target.hurtTime = 0;
                            target.timeUntilRegen = 0;

                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, duration, amplifier));
                        });
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}