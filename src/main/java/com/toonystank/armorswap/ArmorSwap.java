package com.toonystank.armorswap;

import com.toonystank.armorswap.Commands.EnableSwap;
import com.toonystank.armorswap.Events.*;
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
        // Events
        try {
            getServer().getPluginManager().registerEvents(new onJoin(), this);
            getServer().getPluginManager().registerEvents(new clickEvents(), this);

            // Commands
            Objects.requireNonNull(getCommand("Armorswap")).setExecutor(new EnableSwap());

            // Config
            getConfig().options().copyDefaults();
            saveDefaultConfig();

            getLogger().info(ChatColor.AQUA + "ArmorSwap " + ChatColor.UNDERLINE + "Successfully loaded.");
        }catch (Exception e) {
            getLogger().info(ChatColor.RED + "ArmorSwap " + ChatColor.UNDERLINE + "Failed to load.");
        }


    }

}
