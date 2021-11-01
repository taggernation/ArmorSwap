package com.toonystank.armorswap.Commands;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class head implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
        if (sender instanceof Player player) {
            if (player.hasPermission("Armorswap.command.head")) {
                ItemStack itemForHead = player.getInventory().getItemInMainHand();
                ItemStack itemForHand = Objects.requireNonNull(player.getEquipment()).getHelmet();
                player.getEquipment().setHelmet(itemForHead);
                player.getInventory().setItemInMainHand(itemForHand);
                player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
            }
        }

        return true;
    }
}
