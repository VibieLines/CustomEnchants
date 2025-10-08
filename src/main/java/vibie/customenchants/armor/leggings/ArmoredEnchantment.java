package vibie.customenchants.armor.leggings;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class ArmoredEnchantment extends Enchantment {

    public ArmoredEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return 0;
        }
        return level;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof ProtectionEnchantment protectionEnchant) {
            return protectionEnchant.protectionType == ProtectionEnchantment.Type.ALL;
        }
        return super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        boolean hasProtection = enchantments.keySet().stream()
                .anyMatch(enchantment -> {
                    if (enchantment instanceof ProtectionEnchantment protectionEnchant) {
                        return protectionEnchant.protectionType == ProtectionEnchantment.Type.ALL;
                    }
                    return false;
                });

        return hasProtection && super.isAcceptableItem(stack);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}