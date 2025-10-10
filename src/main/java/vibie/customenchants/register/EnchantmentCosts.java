package vibie.customenchants.register;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.Identifier;
import vibie.customenchants.Vibieces;

public class EnchantmentCosts {
    public static final Map<Identifier, Integer> ENCHANTMENT_COSTS = new HashMap<>();
    public static final Map<Identifier, Integer> BASE_COSTS = new HashMap<>();

    static {
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "lifesteal"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "amplifier"), 3);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "clarity"), 1);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "implants"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "vampire"), 1);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "poison_aspect"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "sharper"), 1);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "hearts"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "siphon"), 12);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "explosive"), 12);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "bolt"), 3);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "lava_walker"), 4);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "all_in_one_walker"), 9);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "berserker"), 5);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "axemace"), 15);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "burst"), 10);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "sniper"), 6);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "armored"), 10);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "vacuum"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "execution"), 8);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "revive"), 20);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "endershift"), 7);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "curse_of_weakness"), 1);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "shadow"), 8);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "antitoxin"), 3);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "inferno"), 6);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "shockwave"), 15);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "energizing"), 10);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "gamble"), 12);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "glacier"), 4);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "blessed"), 7);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "springs"), 2);
        ENCHANTMENT_COSTS.put(new Identifier(Vibieces.MOD_ID, "swift"), 5);

        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "lifesteal"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "amplifier"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "clarity"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "implants"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "vampire"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "poison_aspect"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "sharper"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "hearts"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "siphon"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "explosive"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "bolt"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "lava_walker"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "all_in_one_walker"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "berserker"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "axemace"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "burst"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "sniper"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "armored"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "vacuum"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "execution"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "revive"), 5);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "endershift"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "curse_of_weakness"), 1);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "shadow"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "antitoxin"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "inferno"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "shockwave"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "energizing"), 3);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "gamble"), 4);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "glacier"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "blessed"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "springs"), 2);
        BASE_COSTS.put(new Identifier(Vibieces.MOD_ID, "swift"), 5);
    }

    public static int getCost(Identifier enchantmentId) {
        return ENCHANTMENT_COSTS.getOrDefault(enchantmentId, 1);
    }

    public static int getBaseCost(Identifier enchantmentId) {
        return BASE_COSTS.getOrDefault(enchantmentId, 3);
    }
}