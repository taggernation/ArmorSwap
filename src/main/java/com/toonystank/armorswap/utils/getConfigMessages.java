package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class getConfigMessages {

    public static String getPrefix() {
        return ArmorSwap.getPlugin().getConfig().getString("Prefix");
    }
    public static void getDisable(Player p) {
        try {
            p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Disable"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void getEnable(Player p) {
        try {
            p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Enable"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void getWrongusage(Player p) {
        try {
            p.sendMessage(getPrefix() + " " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("usageError"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
