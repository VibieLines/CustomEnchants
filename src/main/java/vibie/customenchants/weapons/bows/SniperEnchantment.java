package vibie.customenchants.weapons.bows;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import vibie.customenchants.register.ModEnchantments;

public class SniperEnchantment extends Enchantment {

    public SniperEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    public static void registerTickCallback() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof ArrowEntity arrow)) return;

            if (!(arrow.getOwner() instanceof LivingEntity shooter)) return;

            ItemStack bow = shooter.getStackInHand(Hand.MAIN_HAND);
            int level = EnchantmentHelper.getLevel(ModEnchantments.SNIPER, bow);
            if (level <= 0) return;

            double speedMultiplier = 1.0 + (0.4 * level);

            arrow.setVelocity(
                    arrow.getVelocity().x * speedMultiplier,
                    arrow.getVelocity().y * speedMultiplier,
                    arrow.getVelocity().z * speedMultiplier
            );
        });
    }
}