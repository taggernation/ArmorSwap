package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class onJoin implements Listener {
    String prefix = ArmorSwap.getPlugin().getConfig().getString("Prefix");
    String enable = ArmorSwap.getPlugin().getConfig().getString("Enable");
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER) == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + " " + ChatColor.translateAlternateColorCodes('&',enable));
            data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 1);
        }
    }
}
