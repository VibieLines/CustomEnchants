package vibie.customenchants.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vibie.customenchants.register.EnchantmentCosts;

import java.util.Map;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Shadow @Final private Property levelCost;

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40))
    private int removeLevelCap(int value) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 39))
    private int removeDisplayCap(int value) {
        return Integer.MAX_VALUE - 1;
    }

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void modifyCustomEnchantmentCost(CallbackInfo ci) {
        AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
        int currentCost = this.levelCost.get();

        if (currentCost > 0) {
            ItemStack outputStack = handler.getSlot(2).getStack();
            if (!outputStack.isEmpty()) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(outputStack);

                int customCostTotal = 0;
                boolean hasCustomEnchants = false;
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    Identifier id = Registries.ENCHANTMENT.getId(enchantment);

                    if (id.getNamespace().equals("vibieces")) {
                        hasCustomEnchants = true;
                        int level = entry.getValue();
                        int enchantCost = EnchantmentCosts.getCost(id);
                        customCostTotal += (enchantCost * level);
                    }
                }

                if (hasCustomEnchants) {
                    int vanillaCost = CVC(enchantments);
                    int newCost = vanillaCost + customCostTotal;
                    this.levelCost.set(newCost);
                }
            }
        }
    }
    @Unique
    private int CVC(Map<Enchantment, Integer> enchantments) {
        int cost = 0;
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            Identifier id = Registries.ENCHANTMENT.getId(enchantment);
            if (!id.getNamespace().equals("vibieces")) {
                int level = entry.getValue();
                switch (enchantment.getRarity()) {
                    case COMMON -> cost += 1 * level;
                    case UNCOMMON -> cost += 2 * level;
                    case RARE -> cost += 4 * level;
                    case VERY_RARE -> cost += 8 * level;
                }
            }
        }
        return Math.max(1, cost);
    }
}