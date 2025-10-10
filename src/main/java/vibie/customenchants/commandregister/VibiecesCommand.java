package vibie.customenchants.commandregister;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class VibiecesCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("vibieces")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        sendPage(source, 1);
                        return Command.SINGLE_SUCCESS;
                    })
                    .then(argument("page", IntegerArgumentType.integer(1, 6))
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();
                                int page = IntegerArgumentType.getInteger(context, "page");
                                sendPage(source, page);
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            );
        });
    }

    private static void sendPage(ServerCommandSource source, int page) {
        source.sendMessage(Text.literal("Vibie's Custom Enchantments").formatted(Formatting.GOLD, Formatting.BOLD));
        source.sendMessage(Text.literal("Page " + page + "/6 - Use /vibieces <page>").formatted(Formatting.GRAY));

        switch (page) {
            case 1:
                sendWeaponsPage1(source);
                break;
            case 2:
                sendWeaponsPage2(source);
                break;
            case 3:
                sendToolsPage(source);
                break;
            case 4:
                sendArmorPage1(source);
                break;
            case 5:
                sendArmorPage2(source);
                break;
            case 6:
                sendCursesPage(source);
                break;
        }
    }

    private static void sendWeaponsPage1(ServerCommandSource source) {
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

        sendEnchantmentEntry(source, "Execution",
                "Chance to instant kill enemies lower then 3 hearts",
                "Enchanting Table, Villager Trades",
                "Max Level: 3, Incompatible with Vampire, Lifesteal, Sharper");
    }

    private static void sendWeaponsPage2(ServerCommandSource source) {
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("WEAPONS 2:").formatted(Formatting.RED, Formatting.BOLD));

        sendEnchantmentEntry(source, "Glacier",
                "Slows targets and has chance to trap them in ice",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        sendEnchantmentEntry(source, "Blessed",
                "5% chance to remove all bad effects, Level 2 adds Regeneration",
                "Enchanting Table, Villager Trades",
                "Max Level: 2, Incompatible with Amplifier");
    }

    private static void sendToolsPage(ServerCommandSource source) {
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

        sendEnchantmentEntry(source, "Shockwave",
                "Deals area damage when falling 10+ blocks (axe only)",
                "Enchanting, Villagers, Random Looting",
                "Max Level: 1");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("BOWS:").formatted(Formatting.DARK_GREEN, Formatting.BOLD));

        sendEnchantmentEntry(source, "Sniper",
                "Increases arrow speed",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("PICKAXES:").formatted(Formatting.GRAY, Formatting.BOLD));

        sendEnchantmentEntry(source, "Explosive",
                "Creates explosion when mining blocks",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");

        sendEnchantmentEntry(source, "Energizing",
                "Gives Haste effect while holding",
                "Structure chests only",
                "Max Level: 2 Haste I/II");

        sendEnchantmentEntry(source, "Gamble",
                "25% chance to upgrade nearby ores, fails turn to coal ore",
                "Enchanting Table, Villager Trades",
                "Max Level: 1, 20min cooldown");
    }

    private static void sendArmorPage1(ServerCommandSource source) {
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

        sendEnchantmentEntry(source, "Antitoxin",
                "Removes Poison and Wither effects",
                "Enchanting Table, Villager Trades",
                "Max Level: 1");

        sendEnchantmentEntry(source, "Shadow",
                "Blinds players for 10 seconds",
                "Enchanting Table, Villager Trades",
                "Max Level: 1, 45 second cooldown");

        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("CHESTPLATES:").formatted(Formatting.GREEN, Formatting.BOLD));

        sendEnchantmentEntry(source, "Hearts",
                "Increases max health (+2 hearts per level)",
                "Craft with 2 Totems + 1 Enchanted Apple + 1 Book, Throw on ground to craft",
                "Max Level: 5, Incompatible with Revive");

        sendEnchantmentEntry(source, "Revive",
                "Prevents death once, healing to 2 hearts with Regeneration, Absorption, Fire Resistance, and Resistance",
                "Ancient City Loot",
                "Max Level: 1, removed after activation, Halves durability, Incompatible with Hearts");

        sendEnchantmentEntry(source, "Berserker",
                "Gives Strength based on low health",
                "Enchanting Table, Villager Trades",
                "Max Level: 1");

        sendEnchantmentEntry(source, "Vacuum",
                "Pulls items toward you",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");
    }
    private static void sendArmorPage2(ServerCommandSource source) {
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

        sendEnchantmentEntry(source, "Inferno",
                "Sets nearby enemies on fire",
                "Enchanting Table, Villager Trades",
                "Max Level: 3, 1-2.5 block radius");

        sendEnchantmentEntry(source, "Swift",
                "Makes u faster by 3% each level",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

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

        sendEnchantmentEntry(source, "Endershift",
                "Automatically teleports you away when below 3 hearts",
                "Enchanting Table, Villager Trades",
                "Max Level: 3");

        sendEnchantmentEntry(source, "Springs",
                "Gives you jumpboost",
                "Enchanting Table, Villager Trades",
                "Max Level: 2");
    }

    private static void sendCursesPage(ServerCommandSource source) {
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("CURSES:").formatted(Formatting.DARK_PURPLE, Formatting.BOLD));

        sendEnchantmentEntry(source, "Curse of Weakness",
                "Reduces attack damage by 0.65 per level",
                "Enchanting Table, Villager Trades",
                "Max Level: 3, Cursed, Incompatible with Sharper");
    }

    private static void sendEnchantmentEntry(ServerCommandSource source, String name, String effect, String location, String notes) {
        source.sendMessage(Text.literal("• " + name).formatted(Formatting.AQUA));
        source.sendMessage(Text.literal("  Effect: " + effect).formatted(Formatting.WHITE));
        source.sendMessage(Text.literal("  Location: " + location).formatted(Formatting.GREEN));
        source.sendMessage(Text.literal("  Notes: " + notes).formatted(Formatting.YELLOW));
        source.sendMessage(Text.literal(""));
    }
}