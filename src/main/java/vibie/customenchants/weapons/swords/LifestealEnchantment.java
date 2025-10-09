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
import vibie.customenchants.register.ModEnchantments;

public class LifestealEnchantment extends Enchantment {

    public LifestealEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    public static void registerAttackCallback() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && !world.isClient && entity instanceof LivingEntity) {
                float cooldown = player.getAttackCooldownProgress(0.0f);
                if (cooldown >= 0.8f) {
                    ItemStack weapon = player.getMainHandStack();
                    int level = EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL, weapon);

                    if (level > 0) {
                        float healAmount = 0.75f * level;

                        int siphonLevel = EnchantmentHelper.getLevel(ModEnchantments.SIPHON, weapon);
                        if (siphonLevel > 0) {
                            healAmount = 0.25f * level;
                        }

                        player.heal(healAmount);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}