package vibie.customenchants.lootinjector;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

public class LootInjector {
    private static final Identifier BURIED_TREASURE_CHEST = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier END_CITY_CHEST = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier NETHER_FORTRESS_CHEST = new Identifier("minecraft", "chests/nether_bridge");
    private static final Identifier WARDEN_LOOT = new Identifier("minecraft", "entities/warden");
    private static final Identifier ANCIENT_CITY_CHEST = new Identifier("minecraft", "chests/ancient_city");

    public static void registerLootpoolModifier() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (ANCIENT_CITY_CHEST.equals(id)) {
                LootPool.Builder revivePool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:revive", 1)))
                        );
                tableBuilder.pool(revivePool.build());
            }

            if (BURIED_TREASURE_CHEST.equals(id)) {
                LootPool.Builder allInOneWalkerPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.07f))
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:all_in_one_walker", 1)))
                                .weight(5)
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:all_in_one_walker", 2)))
                                .weight(4)
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:all_in_one_walker", 3)))
                                .weight(3)
                        );
                tableBuilder.pool(allInOneWalkerPool.build());
            }

            if (END_CITY_CHEST.equals(id) || NETHER_FORTRESS_CHEST.equals(id)) {
                LootPool.Builder axeMacePool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.02f))
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:axemace", 1)))
                                .weight(3)
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:axemace", 2)))
                                .weight(2)
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:axemace", 3)))
                                .weight(1)
                        );
                tableBuilder.pool(axeMacePool.build());

                LootPool.Builder burstPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:burst", 1)))
                                .weight(3)
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:burst", 2)))
                                .weight(2)
                        );
                tableBuilder.pool(burstPool.build());
            }

            if (WARDEN_LOOT.equals(id)) {
                LootPool.Builder armoredPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:armored", 1)))
                                .conditionally(RandomChanceLootCondition.builder(0.5f))
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:armored", 2)))
                                .conditionally(RandomChanceLootCondition.builder(0.4f))
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:armored", 3)))
                                .conditionally(RandomChanceLootCondition.builder(0.3f))
                        )
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK)
                                .apply(SetNbtLootFunction.builder(createEnchantmentNbt("vibieces:armored", 4)))
                                .conditionally(RandomChanceLootCondition.builder(0.4f))
                        );
                tableBuilder.pool(armoredPool.build());
            }
        });
    }

    private static NbtCompound createEnchantmentNbt(String enchantmentId, int level) {
        NbtCompound nbt = new NbtCompound();
        NbtList enchantments = new NbtList();
        NbtCompound enchantment = new NbtCompound();
        enchantment.putString("id", enchantmentId);
        enchantment.putInt("lvl", level);
        enchantments.add(enchantment);
        nbt.put("StoredEnchantments", enchantments);
        return nbt;
    }
}