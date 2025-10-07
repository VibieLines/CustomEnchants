package vibie.customenchants.armor.chestplate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import vibie.customenchants.register.ModEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeartsEnchantment extends Enchantment {

    public HeartsEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }
// forgot to replace testing stuff
    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    public static void registerTickCallback() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                checkCrafting(player);
            }
        });
    }

    private static void checkCrafting(ServerPlayerEntity player) {
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();

        if (world.getTime() % 20 == 0) {
            List<ItemEntity> totems = new ArrayList<>();
            List<ItemEntity> apples = new ArrayList<>();
            List<ItemEntity> books = new ArrayList<>();

            for (ItemEntity entity : world.getEntitiesByClass(ItemEntity.class, new Box(pos).expand(3), item -> true)) {
                ItemStack stack = entity.getStack();
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    totems.add(entity);
                } else if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                    apples.add(entity);
                } else if (stack.getItem() == Items.BOOK) {
                    books.add(entity);
                }
            }

            if (totems.size() >= 2 && apples.size() >= 1 && books.size() >= 1) {
                craftBook(world, pos, 1, totems, apples, books);
            }
        }
    }

    private static void craftBook(World world, BlockPos pos, int level, List<ItemEntity> totems, List<ItemEntity> apples, List<ItemEntity> books) {
        int totemsNeeded = 2;
        int applesNeeded = 1;
        int booksNeeded = 1;

        for (ItemEntity entity : totems) {
            ItemStack stack = entity.getStack();
            int take = Math.min(stack.getCount(), totemsNeeded);
            stack.decrement(take);
            totemsNeeded -= take;
            if (stack.isEmpty()) {
                entity.discard();
            }
            if (totemsNeeded <= 0) break;
        }

        for (ItemEntity entity : apples) {
            ItemStack stack = entity.getStack();
            int take = Math.min(stack.getCount(), applesNeeded);
            stack.decrement(take);
            applesNeeded -= take;
            if (stack.isEmpty()) {
                entity.discard();
            }
            if (applesNeeded <= 0) break;
        }

        for (ItemEntity entity : books) {
            ItemStack stack = entity.getStack();
            int take = Math.min(stack.getCount(), booksNeeded);
            stack.decrement(take);
            booksNeeded -= take;
            if (stack.isEmpty()) {
                entity.discard();
            }
            if (booksNeeded <= 0) break;
        }

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.set(Map.of(ModEnchantments.HEARTS, level), book);

        ItemEntity bookEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, book);
        world.spawnEntity(bookEntity);
    }

    public static void onEquip(PlayerEntity player, ItemStack chestplate) {
        if (player.getWorld().isClient) return;

        int level = EnchantmentHelper.getLevel(ModEnchantments.HEARTS, chestplate);
        if (level > 0) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attribute != null) {
                attribute.setBaseValue(20.0 + (level * 2.0));
            }
        }
    }

    public static void onUnequip(PlayerEntity player, ItemStack chestplate) {
        if (player.getWorld().isClient) return;

        int level = EnchantmentHelper.getLevel(ModEnchantments.HEARTS, chestplate);
        if (level > 0) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attribute != null) {
                attribute.setBaseValue(20.0);
            }
        }
    }
}