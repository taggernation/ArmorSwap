package com.toonystank.armorswap;

import com.toonystank.armorswap.Commands.EnableSwap;
import com.toonystank.armorswap.Events.onRightClick;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ArmorSwap extends JavaPlugin {

    public static ArmorSwap getPlugin() {
        return plugin;
    }

    private static ArmorSwap plugin;

    public static void setPlugin(ArmorSwap plugin) {
        ArmorSwap.plugin = plugin;
    }

    @Override
    public void onEnable() {
        setPlugin(this);
        System.out.println(ChatColor.AQUA + "Hello," + ChatColor.UNDERLINE + " This is the first plugin test by Edward");
        getServer().getPluginManager().registerEvents(new onRightClick(), this);
        Objects.requireNonNull(getCommand("Armor")).setExecutor(new EnableSwap());
    }
}
