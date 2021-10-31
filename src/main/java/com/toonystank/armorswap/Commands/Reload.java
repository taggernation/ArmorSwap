package com.toonystank.armorswap.Commands;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("Armorswap.command.reload")) {
            ArmorSwap.getPlugin().reloadConfig();
            sender.sendMessage(ChatColor.AQUA + "Armorswap is reloaded");
        }
        return true;
    }
}
