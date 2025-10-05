package vibie.customenchants.chestplate.HeartsHandler;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import vibie.customenchants.chestplate.HeartsEnchantment;

public class HeartsEventHandler {

    public static void register() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register((entity, slot, previousStack, currentStack) -> {
            if (entity instanceof ServerPlayerEntity player && slot == EquipmentSlot.CHEST) {
                if (!previousStack.isEmpty()) {
                    HeartsEnchantment.onUnequip(player, previousStack);
                }
                if (!currentStack.isEmpty()) {
                    HeartsEnchantment.onEquip(player, currentStack);
                }
            }
        });
    }
}