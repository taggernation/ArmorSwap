package com.toonystank.armorswap;

import com.toonystank.armorswap.Commands.EnableSwap;
import com.toonystank.armorswap.Commands.Reload;
import com.toonystank.armorswap.Commands.head;
import com.toonystank.armorswap.Events.onItemFrameRightClick;
import com.toonystank.armorswap.Events.onJoin;
import com.toonystank.armorswap.Events.onRightClick;
import com.toonystank.armorswap.Events.onArmorStandRightClick;
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
        // Events
        getServer().getPluginManager().registerEvents(new onRightClick(), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onArmorStandRightClick(), this);
        getServer().getPluginManager().registerEvents(new onItemFrameRightClick(), this);

        // Commands
        Objects.requireNonNull(getCommand("Armor")).setExecutor(new EnableSwap());
        Objects.requireNonNull(getCommand("Head")).setExecutor(new head());
        Objects.requireNonNull(getCommand("armorswapreload")).setExecutor(new Reload());

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

}
