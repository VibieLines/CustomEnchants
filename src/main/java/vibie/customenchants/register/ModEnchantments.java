package vibie.customenchants.register;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vibie.customenchants.Vibieces;
import vibie.customenchants.chestplate.HeartsEnchantment;
import vibie.customenchants.chestplate.HeartsHandler.HeartsEventHandler;
import vibie.customenchants.helmet.ClarityEnchantment;

import vibie.customenchants.helmet.ImplantsEnchantment;
import vibie.customenchants.weapons.*;

public class ModEnchantments {
    public static final Enchantment SIPHON = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "siphon"),
            new SiphonEnchantment()
    );

    public static final Enchantment LIFESTEAL = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "lifesteal"),
            new LifestealEnchantment()
    );

    public static final Enchantment VAMPIRE = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "vampire"),
            new VampireEnchantment()
    );

    public static final Enchantment SHARPER = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "sharper"),
            new SharperEnchantment()
    );

    public static final Enchantment POISON_ASPECT = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "poison_aspect"),
            new PoisonAspectEnchantment()
    );

    public static final Enchantment AMPLIFIER = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "amplifier"),
            new AmplifierEnchantment()
    );

    public static final Enchantment IMPLANTS = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "implants"),
            new ImplantsEnchantment()
    );

    public static final Enchantment CLARITY = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "clarity"),
            new ClarityEnchantment()
    );

    public static final Enchantment HEARTS = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(Vibieces.MOD_ID, "hearts"),
            new HeartsEnchantment()
    );

    public static void register() {
        SiphonEnchantment.registerAttackCallback();
        SiphonEnchantment.registerKillCallback();
        LifestealEnchantment.registerAttackCallback();
        VampireEnchantment.registerAttackCallback();
        PoisonAspectEnchantment.registerAttackCallback();
        AmplifierEnchantment.registerAttackCallback();
        ImplantsEnchantment.registerTickCallback();
        ClarityEnchantment.registerTickCallback();
        HeartsEnchantment.registerTickCallback();
        HeartsEventHandler.register();
    }
}