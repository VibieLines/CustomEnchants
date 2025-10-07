package vibie.customenchants.weapons.swordandaxes;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class SharperEnchantment extends Enchantment {

    public SharperEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return (0.7F + (float)Math.max(0, level - 1) * 0.35F);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof DamageEnchantment damageEnchant) {
            return damageEnchant.typeIndex == 0;
        }
        return super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        boolean hasSharpness = enchantments.keySet().stream()
                .anyMatch(enchantment -> {
                    if (enchantment instanceof DamageEnchantment damageEnchant) {
                        return damageEnchant.typeIndex == 0;
                    }
                    return false;
                });

        return hasSharpness && (super.isAcceptableItem(stack) || stack.getItem() instanceof AxeItem);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }
}