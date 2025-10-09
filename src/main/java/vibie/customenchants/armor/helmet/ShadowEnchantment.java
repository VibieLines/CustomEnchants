package vibie.customenchants.armor.helmet;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import vibie.customenchants.register.ModEnchantments;

import java.util.WeakHashMap;

public class ShadowEnchantment extends Enchantment {

    private static final WeakHashMap<ServerPlayerEntity, Integer> cooldowns = new WeakHashMap<>();

    public ShadowEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinPower(int level) {
        return 15;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
            if (!world.isClient && entity instanceof ServerPlayerEntity target) {
                ItemStack helmet = target.getEquippedStack(EquipmentSlot.HEAD);
                int level = EnchantmentHelper.getLevel(ModEnchantments.SHADOW, helmet);

                if (level > 0) {
                    Integer cooldown = cooldowns.get(target);
                    if (cooldown == null || cooldown <= 0) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0));
                        cooldowns.put(target, 900);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    public static void registerTickCallback() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                Integer cooldown = cooldowns.get(player);
                if (cooldown != null && cooldown > 0) {
                    cooldowns.put(player, cooldown - 1);
                }
            }
        });
    }
}