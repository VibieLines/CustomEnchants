package vibie.customenchants.register;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vibie.customenchants.Vibieces;
import vibie.customenchants.armor.chestplate.VacuumEnchantment;
import vibie.customenchants.armor.leggings.ArmoredEnchantment;
import vibie.customenchants.tools.axes.AxeMace;
import vibie.customenchants.tools.axes.BurstEnchantment;
import vibie.customenchants.armor.boots.AllInOneWalkerEnchantment;
import vibie.customenchants.armor.boots.LavaWalkerEnchantment;
import vibie.customenchants.armor.chestplate.BerserkerEnchantment;
import vibie.customenchants.armor.chestplate.HeartsEnchantment;
import vibie.customenchants.armor.chestplate.HeartsHandler.HeartsEventHandler;
import vibie.customenchants.armor.helmet.ClarityEnchantment;
import vibie.customenchants.armor.helmet.ImplantsEnchantment;
import vibie.customenchants.armor.leggings.BoltEnchantment;
import vibie.customenchants.tools.pickaxes.ExplosiveEnchantment;
import vibie.customenchants.weapons.bows.SniperEnchantment;
import vibie.customenchants.weapons.swordandaxes.SharperEnchantment;
import vibie.customenchants.weapons.swords.*;


public class ModEnchantments {
    // Weapons
    public static final Enchantment SIPHON = register("siphon", new SiphonEnchantment());
    public static final Enchantment LIFESTEAL = register("lifesteal", new LifestealEnchantment());
    public static final Enchantment VAMPIRE = register("vampire", new VampireEnchantment());
    public static final Enchantment POISON_ASPECT = register("poison_aspect", new PoisonAspectEnchantment());
    public static final Enchantment AMPLIFIER = register("amplifier", new AmplifierEnchantment());
    // Sword + Axes
    public static final Enchantment SHARPER = register("sharper", new SharperEnchantment());

    //Axes
    public static final Enchantment AXEMACE = register("axemace", new AxeMace());
    public static final Enchantment BURST = register("burst", new BurstEnchantment());

    // Pickaxes
    public static final Enchantment EXPLOSIVE = register("explosive", new ExplosiveEnchantment());


    //Bows
    public static final Enchantment SNIPER = register("sniper", new SniperEnchantment());

    // Helmets
    public static final Enchantment IMPLANTS = register("implants", new ImplantsEnchantment());
    public static final Enchantment CLARITY = register("clarity", new ClarityEnchantment());

    //Chestplates
    public static final Enchantment HEARTS = register("hearts", new HeartsEnchantment());
    public static final Enchantment BERSERKER = register("berserker", new BerserkerEnchantment());
    public static final Enchantment VACUUM = register("vacuum", new VacuumEnchantment());

    //Leggings
    public static final Enchantment BOLT = register("bolt", new BoltEnchantment());
    public static final Enchantment ARMORED = register("armored", new ArmoredEnchantment());
    //Boots
    public static final Enchantment LAVA_WALKER = register("lava_walker", new LavaWalkerEnchantment());
    public static final Enchantment ALL_IN_ONE_WALKER = register("all_in_one_walker", new AllInOneWalkerEnchantment());

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
        BurstEnchantment.registerAttackCallback();

        //Pickaxes
        ExplosiveEnchantment.registerAttackCallback();

        //Bows
        SniperEnchantment.registerTickCallback();

        // Helmets
        ImplantsEnchantment.registerTickCallback();
        ClarityEnchantment.registerTickCallback();

        //Chestplates
        HeartsEnchantment.registerTickCallback();
        HeartsEventHandler.register();
        BerserkerEnchantment.registerTickCallback();
        VacuumEnchantment.registerTickCallback();

        //Leggings
        BoltEnchantment.registerTickCallback();
        //Boots
        LavaWalkerEnchantment.registerTickCallback();
        AllInOneWalkerEnchantment.registerTickCallback();

    }
}