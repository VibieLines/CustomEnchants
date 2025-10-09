package vibie.customenchants.weapons.swords;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import vibie.customenchants.register.ModEnchantments;

public class GlacierEnchantment extends Enchantment {

    public GlacierEnchantment() {
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
                ItemStack weapon = player.getMainHandStack();
                int level = EnchantmentHelper.getLevel(ModEnchantments.GLACIER, weapon);
                if (level > 0 && cooldown >= 0.8f) {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 0));
                    Random random = world.getRandom();
                    if (random.nextFloat() < 0.05f) {
                        world.getServer().execute(() -> {
                            BlockPos centerPos = target.getBlockPos();
                            for (int x = -1; x <= 1; x++) {
                                for (int y = 0; y <= 2; y++) {
                                    for (int z = -1; z <= 1; z++) {
                                        BlockPos icePos = centerPos.add(x, y, z);
                                        if (world.getBlockState(icePos).isAir()) {
                                            world.setBlockState(icePos, Blocks.ICE.getDefaultState());
                                        }
                                    }
                                }
                            }

                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2));
                        });
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}