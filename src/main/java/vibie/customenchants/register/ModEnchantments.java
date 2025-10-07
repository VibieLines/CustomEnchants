package vibie.customenchants.register;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vibie.customenchants.Vibieces;
import vibie.customenchants.axes.AxeMace;
import vibie.customenchants.boots.AllInOneWalkerEnchantment;
import vibie.customenchants.boots.LavaWalkerEnchantment;
import vibie.customenchants.chestplate.BerserkerEnchantment;
import vibie.customenchants.chestplate.HeartsEnchantment;
import vibie.customenchants.chestplate.HeartsHandler.HeartsEventHandler;
import vibie.customenchants.helmet.ClarityEnchantment;
import vibie.customenchants.helmet.ImplantsEnchantment;
import vibie.customenchants.leggings.BoltEnchantment;
import vibie.customenchants.pickaxes.ExplosiveEnchantment;
import vibie.customenchants.weapons.*;


public class ModEnchantments {
    // Weapons
    public static final Enchantment SIPHON = register("siphon", new SiphonEnchantment());
    public static final Enchantment LIFESTEAL = register("lifesteal", new LifestealEnchantment());
    public static final Enchantment VAMPIRE = register("vampire", new VampireEnchantment());
    public static final Enchantment SHARPER = register("sharper", new SharperEnchantment());
    public static final Enchantment POISON_ASPECT = register("poison_aspect", new PoisonAspectEnchantment());
    public static final Enchantment AMPLIFIER = register("amplifier", new AmplifierEnchantment());

    //Axes
    public static final Enchantment AXEMACE = register("axemace", new AxeMace());

    // Helmets
    public static final Enchantment IMPLANTS = register("implants", new ImplantsEnchantment());
    public static final Enchantment CLARITY = register("clarity", new ClarityEnchantment());

    //Chestplates
    public static final Enchantment HEARTS = register("hearts", new HeartsEnchantment());
    public static final Enchantment BERSERKER = register("berserker", new BerserkerEnchantment());
    //Leggings
    public static final Enchantment BOLT = register("bolt", new BoltEnchantment());

    //Boots
    public static final Enchantment LAVA_WALKER = register("lava_walker", new LavaWalkerEnchantment());
    public static final Enchantment ALL_IN_ONE_WALKER = register("all_in_one_walker", new AllInOneWalkerEnchantment());

    // Pickaxes
    public static final Enchantment EXPLOSIVE = register("explosive", new ExplosiveEnchantment());

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Vibieces.MOD_ID, name), enchantment);
    }

    public static void register() {
        // Weapons
        SiphonEnchantment.registerAttackCallback();
        SiphonEnchantment.registerKillCallback();
        LifestealEnchantment.registerAttackCallback();
        VampireEnchantment.registerAttackCallback();
        PoisonAspectEnchantment.registerAttackCallback();
        AmplifierEnchantment.registerAttackCallback();

        //Axes
        AxeMace.registerAttackCallback();
        AxeMace.registerTickCallback();

        // Helmets
        ImplantsEnchantment.registerTickCallback();
        ClarityEnchantment.registerTickCallback();

        //Chestplates
        HeartsEnchantment.registerTickCallback();
        BerserkerEnchantment.registerTickCallback();
        HeartsEventHandler.register();

        //Leggings
        BoltEnchantment.registerTickCallback();

        //Boots
        LavaWalkerEnchantment.registerTickCallback();
        AllInOneWalkerEnchantment.registerTickCallback();

        //Pickaxes
        ExplosiveEnchantment.registerAttackCallback();
    }
}