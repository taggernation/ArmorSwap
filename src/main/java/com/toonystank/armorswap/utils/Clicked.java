package com.toonystank.armorswap.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Clicked {
    public static void playerItem(Player player, ItemStack item, String sound){

        if (item.getType().toString().toLowerCase().contains("helmet")) {
            ItemStack returnItem = player.getInventory().getHelmet();
            if (returnItem == null) return;
            player.getInventory().setItemInMainHand(returnItem);
            player.getInventory().setHelmet(item);
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }
        else if (item.getType().toString().toLowerCase().contains("chestplate") || item.getType().toString().toLowerCase().contains("elytra")) {
            ItemStack returnItem = player.getInventory().getChestplate();
            if (returnItem == null) return;
            player.getInventory().setItemInMainHand(returnItem);
            player.getInventory().setChestplate(item);
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }
        else if (item.getType().toString().toLowerCase().contains("boots")) {
            ItemStack returnItem = player.getInventory().getBoots();
            if (returnItem == null) return;
            player.getInventory().setItemInMainHand(returnItem);
            player.getInventory().setBoots(item);
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }
        else if (item.getType().toString().toLowerCase().contains("leggings")) {
            ItemStack returnItem = player.getInventory().getLeggings();
            if (returnItem == null) return;
            player.getInventory().setItemInMainHand(returnItem);
            player.getInventory().setLeggings(item);
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }
        else if (item.getType().toString().toLowerCase().contains("elytra")) {
            ItemStack returnItem = player.getInventory().getChestplate();
            if (returnItem == null) return;
            player.getInventory().setItemInMainHand(returnItem);
            player.getInventory().setChestplate(item);
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }
    }
}
