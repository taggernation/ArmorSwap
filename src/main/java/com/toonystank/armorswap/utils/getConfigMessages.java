package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;

public class getConfigMessages {

    public static String getPrefix() {
        return ArmorSwap.getPlugin().getConfig().getString("Prefix");
    }
    public static void getDisable(Player p) {
        try {
            String message = ArmorSwap.getPlugin().getConfig().getString("Disable");
            if (message != null) {
                p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', message));
            }
        } catch (Exception e) {
            PluginLogger.getLogger(e.getMessage());
        }
    }
    public static void getEnable(Player p) {
        try {
            String message = ArmorSwap.getPlugin().getConfig().getString("Enable");
            if (message != null) {
                p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', message));
            }
        } catch (Exception e) {
            PluginLogger.getLogger(e.getMessage());
        }
    }
    public static void getWrongusage(Player p) {
        try {
            String message = ArmorSwap.getPlugin().getConfig().getString("usageError");
            if (message != null) {
                p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', message));
            }
        } catch (Exception e) {
            PluginLogger.getLogger(e.getMessage());
        }
    }
}


