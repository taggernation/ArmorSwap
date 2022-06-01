package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.enums.ConfigDataType;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class ConfigData extends ConfigManager{

    Plugin plugin;
    public ConfigData(Plugin plugin) throws IOException {
        super(plugin, "config.yml", false, true);
        this.plugin = plugin;
    }
    public String getMessage(ConfigDataType type) {
        return ChatColor.translateAlternateColorCodes('&',getString(type.getName()));
    }
    public boolean getBoolean(ConfigDataType type) {
        return getBoolean(type.getName());
    }
}


