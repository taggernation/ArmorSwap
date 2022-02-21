package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.getConfigMessages;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;


public class OnJoin implements Listener {


    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Plugin plugin = ArmorSwap.getPlugin();
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (data.get(new NamespacedKey(plugin, "ArmorSwapEnabled"), PersistentDataType.INTEGER) == null) {
            getConfigMessages.getEnable(p);
            data.set(new NamespacedKey(plugin, "ArmorSwapEnabled"), PersistentDataType.INTEGER, 1);
        }
    }
}