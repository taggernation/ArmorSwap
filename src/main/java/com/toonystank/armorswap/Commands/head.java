package com.toonystank.armorswap.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class head implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player.hasPermission("Armorswap.command.head")) {
                ItemStack itemForHead = player.getInventory().getItemInMainHand();
                ItemStack itemForHand = Objects.requireNonNull(player.getEquipment()).getHelmet();
                player.getEquipment().setHelmet(itemForHead);
                player.getInventory().setItemInMainHand(itemForHand);
            }
        }

        return true;
    }
}
