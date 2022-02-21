package com.toonystank.armorswap.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class PlayerData {
    public final Map<Player, Map<DataType, ItemStack>> player = new HashMap<>();
    public final Map<ArmorStand, Map<DataType, ItemStack>> stand = new HashMap<>();
    public final Map<DataType, ItemStack> item = new HashMap<>();
    public Map<Player, Map<DataType, ItemStack>> getPlayer() {
        return player;
    }

    public Map<DataType, ItemStack> getItem() {
        return item;
    }
    public static void addItem(DataType type, Player player, Map<DataType, ItemStack> map) {
        player.sendMessage("Adding " + type.toString());
        switch (type) {
            case Player_Boots, Player_Leggings, Player_ChestPlate, Player_Helmet, Player_MainHand, Player_OffHand -> map.put(type, getItem(type, player));
        }
    }
    public static void addItem(DataType type, ArmorStand stand, Map<DataType, ItemStack> map) {
        switch (type) {
            case Stand_Boots, Stand_Leggings, Stand_ChestPlate, Stand_Helmet, Stand_MainHand, Stand_OffHand -> map.put(type, getItem(type, stand));
        }
    }
    public static ItemStack getMapItem(DataType type, Map<DataType, ItemStack> map) {
        switch (type) {
            case Player_Boots, Player_Leggings, Player_Helmet, Player_ChestPlate, Stand_Boots, Stand_Leggings, Stand_ChestPlate, Stand_Helmet, Stand_OffHand, Stand_MainHand, Player_MainHand, Player_OffHand -> {
                return map.get(type);
            }
        }
        return null;
    }
    public static ItemStack getItem(DataType type, Player player) {
        switch (type) {
            case Player_Boots -> {
                return Objects.requireNonNull(player.getEquipment()).getBoots();
            }
            case Player_Leggings -> {
                return Objects.requireNonNull(player.getEquipment()).getLeggings();
            }
            case Player_ChestPlate -> {
                return Objects.requireNonNull(player.getEquipment()).getChestplate();
            }
            case Player_Helmet -> {
                return Objects.requireNonNull(player.getEquipment()).getHelmet();
            }
            case Player_MainHand -> {
                return Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            }
            case Player_OffHand -> {
                return Objects.requireNonNull(player.getEquipment()).getItemInOffHand();
            }
        }
        return null;
    }
    public static ItemStack getItem(DataType type, ArmorStand stand) {
        switch (type) {
            case Stand_Boots -> {
                return Objects.requireNonNull(stand.getEquipment()).getBoots();
            }
            case Stand_Leggings -> {
                return Objects.requireNonNull(stand.getEquipment()).getLeggings();
            }
            case Stand_ChestPlate -> {
                return Objects.requireNonNull(stand.getEquipment()).getChestplate();
            }
            case Stand_Helmet -> {
                return Objects.requireNonNull(stand.getEquipment()).getHelmet();
            }
            case Stand_MainHand -> {
                return Objects.requireNonNull(stand.getEquipment()).getItemInMainHand();
            }
            case Stand_OffHand -> {
                return Objects.requireNonNull(stand.getEquipment()).getItemInOffHand();
            }
        }
        return null;
    }
    public static void storeData(Player player, Map<DataType, ItemStack> map) {
        addItem(DataType.Player_Boots, player, map);
        addItem(DataType.Player_Leggings, player, map);
        addItem(DataType.Player_ChestPlate, player, map);
        addItem(DataType.Player_Helmet, player, map);
        addItem(DataType.Player_MainHand, player, map);
        addItem(DataType.Player_OffHand, player, map);
    }
    public static void storeData(ArmorStand stand, Map<DataType, ItemStack> map) {
        addItem(DataType.Stand_Boots, stand, map);
        addItem(DataType.Stand_Leggings, stand, map);
        addItem(DataType.Stand_ChestPlate, stand, map);
        addItem(DataType.Stand_Helmet, stand, map);
        addItem(DataType.Stand_MainHand, stand, map);
        addItem(DataType.Stand_OffHand, stand, map);
    }
    public static void setEquipment(DataType type, ArmorStand stand, Map<DataType, ItemStack> map) {
        stand.sendMessage("Equipment: " + type.toString());
        switch (type) {
            case Player_Leggings -> Objects.requireNonNull(stand.getEquipment()).setLeggings(map.get(type));
            case Player_Boots -> Objects.requireNonNull(stand.getEquipment()).setBoots(map.get(type));
            case Player_ChestPlate -> Objects.requireNonNull(stand.getEquipment()).setChestplate(map.get(type));
            case Player_Helmet -> Objects.requireNonNull(stand.getEquipment()).setHelmet(map.get(type));
            case Player_MainHand -> Objects.requireNonNull(stand.getEquipment()).setItemInMainHand(map.get(type));
            case Player_OffHand -> Objects.requireNonNull(stand.getEquipment()).setItemInOffHand(map.get(type));
        }
    }
    public static void setEquipment(DataType type, Player player, Map<DataType, ItemStack> map) {
        switch (type) {
            case Stand_Leggings -> Objects.requireNonNull(player.getEquipment()).setLeggings(map.get(type));
            case Stand_Boots -> Objects.requireNonNull(player.getEquipment()).setBoots(map.get(type));
            case Stand_ChestPlate -> Objects.requireNonNull(player.getEquipment()).setChestplate(map.get(type));
            case Stand_Helmet -> Objects.requireNonNull(player.getEquipment()).setHelmet(map.get(type));
            case Stand_MainHand -> Objects.requireNonNull(player.getEquipment()).setItemInMainHand(map.get(type));
            case Stand_OffHand -> Objects.requireNonNull(player.getEquipment()).setItemInOffHand(map.get(type));
        }
    }
    public static boolean canMove(DataType type, Map<DataType, ItemStack> map) {
        ItemStack item = PlayerData.getMapItem(type, map);
        if (item == null) return true;
        if (item.getType().equals(Material.AIR)) return true;
        return !item.containsEnchantment(Enchantment.BINDING_CURSE);
    }

}
