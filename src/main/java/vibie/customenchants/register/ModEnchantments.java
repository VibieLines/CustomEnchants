package vibie.customenchants.register;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vibie.customenchants.Vibieces;
import vibie.customenchants.armor.boots.EndershiftEnchantment;
import vibie.customenchants.armor.boots.SpringsEnchantment;
import vibie.customenchants.armor.chestplate.ReviveEnchantment;
import vibie.customenchants.armor.chestplate.VacuumEnchantment;
import vibie.customenchants.armor.helmet.AntitoxinEnchantment;
import vibie.customenchants.armor.helmet.ShadowEnchantment;
import vibie.customenchants.armor.leggings.ArmoredEnchantment;
import vibie.customenchants.armor.leggings.InfernoEnchantment;
import vibie.customenchants.armor.leggings.SwiftEnchantment;
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
import vibie.customenchants.tools.axes.ShockwaveEnchantment;
import vibie.customenchants.tools.pickaxes.EnergizingEnchantment;
import vibie.customenchants.tools.pickaxes.ExplosiveEnchantment;
import vibie.customenchants.tools.pickaxes.GambleEnchantment;
import vibie.customenchants.weapons.bows.SniperEnchantment;
import vibie.customenchants.weapons.curses.CurseOfWeaknessEnchantment;
import vibie.customenchants.weapons.swordandaxes.SharperEnchantment;
import vibie.customenchants.weapons.swords.*;


public class ModEnchantments {
    // Weapons
    public static final Enchantment SIPHON = register("siphon", new SiphonEnchantment());
    public static final Enchantment LIFESTEAL = register("lifesteal", new LifestealEnchantment());
    public static final Enchantment VAMPIRE = register("vampire", new VampireEnchantment());
    public static final Enchantment POISON_ASPECT = register("poison_aspect", new PoisonAspectEnchantment());
    public static final Enchantment AMPLIFIER = register("amplifier", new AmplifierEnchantment());
    public static final Enchantment EXECUTION = register("execution", new ExecutionEnchantment());
    public static final Enchantment GLACIER = register("glacier", new GlacierEnchantment());
    public static final Enchantment BLESSED = register("blessed", new BlessedEnchantment());

    // Sword + Axes
    public static final Enchantment SHARPER = register("sharper", new SharperEnchantment());
    public static final Enchantment CURSE_OF_WEAKNESS = register("curse_of_weakness", new CurseOfWeaknessEnchantment());

    //Axes
    public static final Enchantment AXEMACE = register("axemace", new AxeMace());
    public static final Enchantment BURST = register("burst", new BurstEnchantment());
    public static final Enchantment SHOCKWAVE = register("shockwave", new ShockwaveEnchantment());

    // Pickaxes
    public static final Enchantment EXPLOSIVE = register("explosive", new ExplosiveEnchantment());
    public static final Enchantment ENERGIZING = register("energizing", new EnergizingEnchantment());
    public static final Enchantment GAMBLE = register("gamble", new GambleEnchantment());

    //Bows
    public static final Enchantment SNIPER = register("sniper", new SniperEnchantment());

    // Helmets
    public static final Enchantment IMPLANTS = register("implants", new ImplantsEnchantment());
    public static final Enchantment CLARITY = register("clarity", new ClarityEnchantment());
    public static final Enchantment SHADOW = register("shadow", new ShadowEnchantment());
    public static final Enchantment ANTITOXIN = register("antitoxin", new AntitoxinEnchantment());

    //Chestplates
    public static final Enchantment HEARTS = register("hearts", new HeartsEnchantment());
    public static final Enchantment BERSERKER = register("berserker", new BerserkerEnchantment());
    public static final Enchantment VACUUM = register("vacuum", new VacuumEnchantment());
    public static final Enchantment REVIVE = register("revive", new ReviveEnchantment());

    //Leggings
    public static final Enchantment BOLT = register("bolt", new BoltEnchantment());
    public static final Enchantment ARMORED = register("armored", new ArmoredEnchantment());
    public static final Enchantment INFERNO = register("inferno", new InfernoEnchantment());
    public static final Enchantment SWIFT = register("swift", new SwiftEnchantment());

    //Boots
    public static final Enchantment LAVA_WALKER = register("lava_walker", new LavaWalkerEnchantment());
    public static final Enchantment ALL_IN_ONE_WALKER = register("all_in_one_walker", new AllInOneWalkerEnchantment());
    public static final Enchantment ENDERSHIFT = register("endershift", new EndershiftEnchantment());
    public static final Enchantment SPRINGS = register("springs", new SpringsEnchantment());

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
        ExecutionEnchantment.registerAttackCallback();
        GlacierEnchantment.registerAttackCallback();
        BlessedEnchantment.registerAttackCallback();

        //Axes
        AxeMace.registerAttackCallback();
        AxeMace.registerTickCallback();
        ShockwaveEnchantment.registerAttackCallback();
        ShockwaveEnchantment.registerTickCallback();
        BurstEnchantment.registerAttackCallback();

        //Pickaxes
        ExplosiveEnchantment.registerAttackCallback();
        EnergizingEnchantment.registerTickCallback();
        GambleEnchantment.registerBlockBreakCallback();

        //Bows
        SniperEnchantment.registerTickCallback();

        // Helmets
        ImplantsEnchantment.registerTickCallback();
        ClarityEnchantment.registerTickCallback();
        ShadowEnchantment.registerTickCallback();
        ShadowEnchantment.registerAttackCallback();
        AntitoxinEnchantment.registerTickCallback();

        //Chestplates
        HeartsEnchantment.registerTickCallback();
        HeartsEventHandler.register();
        BerserkerEnchantment.registerTickCallback();
        VacuumEnchantment.registerTickCallback();
        ReviveEnchantment.registerDamageCallback();

        //Leggings
        BoltEnchantment.registerTickCallback();
        InfernoEnchantment.registerTickCallback();
        SwiftEnchantment.registerTickCallback();

        //Boots
        LavaWalkerEnchantment.registerTickCallback();
        AllInOneWalkerEnchantment.registerTickCallback();
        EndershiftEnchantment.registerTickCallback();
        SpringsEnchantment.registerTickCallback();
    }
}