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

    public Map<DataType, ItemStack> item;

    public PlayerData() {
        this.item = new HashMap<>();
    }
    public Map<DataType, ItemStack> getItem() {
        return item;
    }
    public static void addItem(DataType type, Player player, Map<DataType, ItemStack> map) {
        switch (type) {
            case PLAYER_BOOTS, PLAYER_LEGGINGS, PLAYER_CHEST_PLATE, PLAYER_HELMET, PLAYER_MAIN_HAND, PLAYER_OFF_HAND -> map.put(type, getItem(type, player));
        }
    }
    public static void addItem(DataType type, ArmorStand stand, Map<DataType, ItemStack> map) {
        switch (type) {
            case STAND_BOOTS, STAND_LEGGINGS, STAND_CHEST_PLATE, STAND_HELMET, STAND_MAIN_HAND, STAND_OFF_HAND -> map.put(type, getItem(type, stand));
        }
    }
    public static ItemStack getMapItem(DataType type, Map<DataType, ItemStack> map) {
        switch (type) {
            case PLAYER_BOOTS, PLAYER_LEGGINGS, PLAYER_HELMET, PLAYER_CHEST_PLATE, STAND_BOOTS, STAND_LEGGINGS, STAND_CHEST_PLATE, STAND_HELMET, STAND_OFF_HAND, STAND_MAIN_HAND, PLAYER_MAIN_HAND, PLAYER_OFF_HAND -> {
                return map.get(type);
            }
        }
        return null;
    }
    public static ItemStack getItem(DataType type, Player player) {
        switch (type) {
            case PLAYER_BOOTS -> {
                return Objects.requireNonNull(player.getEquipment()).getBoots();
            }
            case PLAYER_LEGGINGS -> {
                return Objects.requireNonNull(player.getEquipment()).getLeggings();
            }
            case PLAYER_CHEST_PLATE -> {
                return Objects.requireNonNull(player.getEquipment()).getChestplate();
            }
            case PLAYER_HELMET -> {
                return Objects.requireNonNull(player.getEquipment()).getHelmet();
            }
            case PLAYER_MAIN_HAND -> {
                return Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            }
            case PLAYER_OFF_HAND -> {
                return Objects.requireNonNull(player.getEquipment()).getItemInOffHand();
            }
        }
        return null;
    }
    public static ItemStack getItem(DataType type, ArmorStand stand) {
        switch (type) {
            case STAND_BOOTS -> {
                return Objects.requireNonNull(stand.getEquipment()).getBoots();
            }
            case STAND_LEGGINGS -> {
                return Objects.requireNonNull(stand.getEquipment()).getLeggings();
            }
            case STAND_CHEST_PLATE -> {
                return Objects.requireNonNull(stand.getEquipment()).getChestplate();
            }
            case STAND_HELMET -> {
                return Objects.requireNonNull(stand.getEquipment()).getHelmet();
            }
            case STAND_MAIN_HAND -> {
                return Objects.requireNonNull(stand.getEquipment()).getItemInMainHand();
            }
            case STAND_OFF_HAND -> {
                return Objects.requireNonNull(stand.getEquipment()).getItemInOffHand();
            }
        }
        return null;
    }
    public static void storeData(Player player, Map<DataType, ItemStack> map) {
        addItem(DataType.PLAYER_BOOTS, player, map);
        addItem(DataType.PLAYER_LEGGINGS, player, map);
        addItem(DataType.PLAYER_CHEST_PLATE, player, map);
        addItem(DataType.PLAYER_HELMET, player, map);
        addItem(DataType.PLAYER_MAIN_HAND, player, map);
        addItem(DataType.PLAYER_OFF_HAND, player, map);
    }
    public static void storeData(ArmorStand stand, Map<DataType, ItemStack> map) {
        addItem(DataType.STAND_BOOTS, stand, map);
        addItem(DataType.STAND_LEGGINGS, stand, map);
        addItem(DataType.STAND_CHEST_PLATE, stand, map);
        addItem(DataType.STAND_HELMET, stand, map);
        addItem(DataType.STAND_MAIN_HAND, stand, map);
        addItem(DataType.STAND_OFF_HAND, stand, map);
    }
    public static void setEquipment(DataType type, ArmorStand stand, Map<DataType, ItemStack> map) {
        switch (type) {
            case PLAYER_LEGGINGS -> Objects.requireNonNull(stand.getEquipment()).setLeggings(map.get(type));
            case PLAYER_BOOTS -> Objects.requireNonNull(stand.getEquipment()).setBoots(map.get(type));
            case PLAYER_CHEST_PLATE -> Objects.requireNonNull(stand.getEquipment()).setChestplate(map.get(type));
            case PLAYER_HELMET -> Objects.requireNonNull(stand.getEquipment()).setHelmet(map.get(type));
            case PLAYER_MAIN_HAND -> Objects.requireNonNull(stand.getEquipment()).setItemInMainHand(map.get(type));
            case PLAYER_OFF_HAND -> Objects.requireNonNull(stand.getEquipment()).setItemInOffHand(map.get(type));
        }
    }
    public static void setEquipment(DataType type, Player player, Map<DataType, ItemStack> map) {
        switch (type) {
            case STAND_LEGGINGS -> Objects.requireNonNull(player.getEquipment()).setLeggings(map.get(type));
            case STAND_BOOTS -> Objects.requireNonNull(player.getEquipment()).setBoots(map.get(type));
            case STAND_CHEST_PLATE -> Objects.requireNonNull(player.getEquipment()).setChestplate(map.get(type));
            case STAND_HELMET -> Objects.requireNonNull(player.getEquipment()).setHelmet(map.get(type));
            case STAND_MAIN_HAND -> Objects.requireNonNull(player.getEquipment()).setItemInMainHand(map.get(type));
            case STAND_OFF_HAND -> Objects.requireNonNull(player.getEquipment()).setItemInOffHand(map.get(type));
        }
    }
    public static boolean canMove(DataType type, Map<DataType, ItemStack> map) {
        ItemStack item = PlayerData.getMapItem(type, map);
        if (item == null) return true;
        if (item.getType().equals(Material.AIR)) return true;
        return !item.containsEnchantment(Enchantment.BINDING_CURSE);
    }

}
