package com.toonystank.armorswap.utils;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Clicked {
    public static void playerItem(Player player, ItemStack item, String sound){
        ItemStack returnItem = player.getInventory().getChestplate();
        if (returnItem == null) return;
        if (returnItem.containsEnchantment(Enchantment.BINDING_CURSE)) return;
        player.getInventory().setItemInMainHand(returnItem);
        player.getInventory().setChestplate(item);
        player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
    }
}
