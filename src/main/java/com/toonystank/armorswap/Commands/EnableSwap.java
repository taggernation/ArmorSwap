package com.toonystank.armorswap.Commands;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.getConfigMessages;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@Deprecated
public class EnableSwap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("Reload")) {
                    if (sender.hasPermission("Armorswap.command.reload")) {
                        ArmorSwap.getPlugin().reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.AQUA + " ArmorSwap is reloaded");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    }
                }
                else if (args[0].equalsIgnoreCase("head")) {
                    if (player.hasPermission("Armorswap.command.head")) {
                        ItemStack itemForHead = player.getInventory().getItemInMainHand();
                        ItemStack itemForHand = Objects.requireNonNull(player.getEquipment()).getHelmet();
                        player.getEquipment().setHelmet(itemForHead);
                        player.getInventory().setItemInMainHand(itemForHand);
                        player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.RED + "You don't have permission to use this command!");
                    }
                }
                else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "|              |" + "\n" + "\n" + ChatColor.YELLOW + "* /Armorswap reload - Reloads the plugin" + "\n" + "* /Armorswap head - Replace player head with holding item" + "\n" + "\n" + ChatColor.AQUA + "" + ChatColor.RESET + "" + "|              |");
                }
                else {
                    getConfigMessages.getWrongUsage(player);
                }
            }
            else {
                PersistentDataContainer data = player.getPersistentDataContainer();
                if (!data.has(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER)) {
                    data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 0);
                }
                else {
                    try {
                        int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
                        if (value == 1) {
                            getConfigMessages.getDisable(player);
                            data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 0);
                        }else {
                            getConfigMessages.getEnable(player);
                            data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 1);
                        }
                    }
                    catch (Exception value){
                        System.out.println(value.getMessage());
                    }
                }
            }
        }
        return true;
    }
}
