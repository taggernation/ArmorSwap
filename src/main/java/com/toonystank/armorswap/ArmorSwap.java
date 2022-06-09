package com.toonystank.armorswap;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import com.toonystank.armorswap.Commands.CommandManager;
import com.toonystank.armorswap.Events.ClickEvent;
import com.toonystank.armorswap.Events.OnJoin;
import com.toonystank.armorswap.enums.ConfigDataType;
import com.toonystank.armorswap.utils.ConfigData;
import com.toonystank.armorswap.utils.ConfigManager;
import com.toonystank.armorswap.utils.Metrics;
import com.toonystank.armorswap.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class ArmorSwap extends JavaPlugin {

    public static ArmorSwap getPlugin() {
        return plugin;
    }
    String configVersion = "1.5.9";

    private static ArmorSwap plugin;
    private ConfigData configData;
    public ConfigData getConfigData() {
        return configData;
    }

    public static void setPlugin(ArmorSwap plugin) {
        ArmorSwap.plugin = plugin;
    }

    @Override
    public void onEnable() {
        try {
            configData = new ConfigData(this);
            configData.updateConfig(configVersion, ConfigDataType.VERSION.getName());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
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
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        try {
            getServer().getPluginManager().registerEvents(new ClickEvent(configData,this), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Checks if server is running on Paper or not
        boolean isOnPaper = false;
        try {
            Class.forName("com.destroystokyo.paper");
            isOnPaper = true;
        } catch (ClassNotFoundException ignored) {
        }

//        Initializing Command manager
        if (isOnPaper) {
            PaperCommandManager pcm = new PaperCommandManager(this);
            pcm.registerCommand(new CommandManager(configData, this));
        } else {
            BukkitCommandManager bcm = new BukkitCommandManager(this);
            bcm.registerCommand(new CommandManager(configData, this));
        }

        // Config

        getLogger().info(ChatColor.AQUA + "ArmorSwap " + ChatColor.UNDERLINE + "Successfully loaded.");
    }

}
