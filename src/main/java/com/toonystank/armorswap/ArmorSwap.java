package com.toonystank.armorswap;

import com.toonystank.armorswap.Events.onRightClick;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorSwap extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println(ChatColor.AQUA + "Hello," + ChatColor.UNDERLINE + " This is the first plugin test by Edward");
        getServer().getPluginManager().registerEvents(new onRightClick(), this);
    }
}
