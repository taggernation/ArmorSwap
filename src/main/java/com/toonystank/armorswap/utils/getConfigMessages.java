package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class getConfigMessages {

    public static String getPrefix() {
        return ArmorSwap.getPlugin().getConfig().getString("Prefix");
    }

    public static void getDisable(Player p) {
        String message = ArmorSwap.getPlugin().getConfig().getString("Disable");
        if (message != null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    public static void getEnable(Player p) {
        String message = ArmorSwap.getPlugin().getConfig().getString("Enable");
        if (message != null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    public static void getWrongUsage(Player p) {
        String message = ArmorSwap.getPlugin().getConfig().getString("usageError");
        if (message != null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}


