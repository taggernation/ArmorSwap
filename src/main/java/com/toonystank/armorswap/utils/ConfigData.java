package com.toonystank.armorswap.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class ConfigData extends ConfigManager{

    Plugin plugin;
    public ConfigData(Plugin plugin) throws IOException {
        super(plugin, "config.yml", false, true);
        this.plugin = plugin;
    }
    public String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&',getString(path));
    }
}


