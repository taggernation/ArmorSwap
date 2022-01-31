package com.toonystank.armorswap;

import com.toonystank.armorswap.Commands.EnableSwap;
import com.toonystank.armorswap.Events.*;
import com.toonystank.armorswap.utils.Metrics;
import com.toonystank.armorswap.utils.UpdateChecker;
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
        new UpdateChecker(this, 97332).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available." + "\n" + ChatColor.RED + "https://www.spigotmc.org/resources/armorswap-swap-items-by-right-clicking.97332/");
            }
        });
        //bStats metrics
        Metrics metrics = new Metrics(this, 14140); //https://bstats.org/what-is-my-plugin-id
        
        setPlugin(this);
        // Events
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new clickEvents(), this);

        // Commands
        Objects.requireNonNull(getCommand("Armorswap")).setExecutor(new EnableSwap());

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getLogger().info(ChatColor.AQUA + "ArmorSwap " + ChatColor.UNDERLINE + "Successfully loaded.");
    }

}
