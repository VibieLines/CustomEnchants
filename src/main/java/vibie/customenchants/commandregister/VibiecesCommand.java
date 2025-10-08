package vibie.customenchants.commandregister;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;

public class VibiecesCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("vibieces")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        sendEnchantmentInfo(source);
                        return Command.SINGLE_SUCCESS;
                    })
            );
        });
    }

    private static void sendEnchantmentInfo(ServerCommandSource source) {
        source.sendMessage(Text.literal("Vibie's Custom Enchantments").formatted(Formatting.GOLD, Formatting.BOLD));

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("WEAPONS:").formatted(Formatting.RED, Formatting.BOLD));

        sendEnchantmentEntry(source, "Siphon",
                "Grants absorption when hitting the same target multiple times in air",
                "Enchanting Table, Villager Trades",
                "Max Level: 1, Incompatible with Vampire");

        sendEnchantmentEntry(source, "Lifesteal",
                "Heals you on hit (reduced if Siphon is also on weapon)",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

        sendEnchantmentEntry(source, "Vampire",
                "25% chance to heal and deal extra magic damage",
                "Enchanting Table, Villager Trades",
                "Max Level: 4, Incompatible with Siphon");

        sendEnchantmentEntry(source, "Sharper",
                "Extra damage (works with Sharpness on axes)",
                "Villager Trades only",
                "Max Level: 5, Requires Sharpness");

        sendEnchantmentEntry(source, "Poison Aspect",
                "Poisons targets on hit",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        sendEnchantmentEntry(source, "Amplifier",
                "15% chance to modify target's status effects",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("AXES:").formatted(Formatting.DARK_RED, Formatting.BOLD));

        sendEnchantmentEntry(source, "Axe Mace",
                "Deals bonus damage based on fall distance (axe only)",
                "End City & Nether Fortress chests (Very rare btw)",
                "Max Level: 3");

        sendEnchantmentEntry(source, "Burst",
                "Launch upward when attacking while crouching and offground (axe only)",
                "End City & Nether Fortress chests",
                "Max Level: 2");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("BOWS:").formatted(Formatting.DARK_GREEN, Formatting.BOLD));

        sendEnchantmentEntry(source, "Sniper",
                "Increases arrow speed",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("HELMETS:").formatted(Formatting.BLUE, Formatting.BOLD));

        sendEnchantmentEntry(source, "Implants",
                "Restores hunger over time",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

        sendEnchantmentEntry(source, "Clarity",
                "Removes Mining Fatigue effect",
                "Enchanting Table, Villager Trades",
                "Max Level: 1");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("CHESTPLATES:").formatted(Formatting.GREEN, Formatting.BOLD));

        sendEnchantmentEntry(source, "Hearts",
                "Increases max health (+2 hearts per level)",
                "Craft with 2 Totems + 1 Enchanted Apple + 1 Book, Throw on ground to craft",
                "Max Level: 5");

        sendEnchantmentEntry(source, "Berserker",
                "Gives Strength based on low health",
                "Enchanting Table, Villager Trades",
                "Max Level: 1");

        sendEnchantmentEntry(source, "Vacuum",
                "Pulls items toward you",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("LEGGINGS:").formatted(Formatting.YELLOW, Formatting.BOLD));

        sendEnchantmentEntry(source, "Bolt",
                "Gives permanent Speed effect",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        sendEnchantmentEntry(source, "Armored",
                "Adds +1 protection per level stacks with Protection",
                "Warden drop 50%, Each level has a diff drop chance.",
                "Max Level: 4");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("BOOTS:").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD));

        sendEnchantmentEntry(source, "Lava Walker",
                "Turns lava into magma blocks and gives fire resistance",
                "Enchanting Table, Villager Trades",
                "Max Level: 3, Incompatible with Frost Walker");

        sendEnchantmentEntry(source, "All In One Walker",
                "Turns lava AND water, gives fire resistance",
                "Buried Treasure chests",
                "Max Level: 3");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("PICKAXES:").formatted(Formatting.GRAY, Formatting.BOLD));

        sendEnchantmentEntry(source, "Explosive",
                "Creates explosion when mining blocks",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        source.sendMessage(Text.literal(""));
    }

    private static void sendEnchantmentEntry(ServerCommandSource source, String name, String effect, String location, String notes) {
        source.sendMessage(Text.literal("• " + name).formatted(Formatting.AQUA));
        source.sendMessage(Text.literal("  Effect: " + effect).formatted(Formatting.WHITE));
        source.sendMessage(Text.literal("  Location: " + location).formatted(Formatting.GREEN));
        source.sendMessage(Text.literal("  Notes: " + notes).formatted(Formatting.YELLOW));
        source.sendMessage(Text.literal(""));
    }
}