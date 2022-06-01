package com.toonystank.armorswap.utils;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class PlayerData extends ConfigManager{

    public PlayerData(Plugin plugin, Player player, ArmorStand stand) throws IOException {
        super(plugin, player.getUniqueId() + ".yml", "playerdata", false, false);
    }

}
