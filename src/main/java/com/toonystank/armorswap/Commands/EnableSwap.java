package com.toonystank.armorswap.Commands;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class EnableSwap implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // enable command
        if (sender instanceof Player player) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!data.has(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER)) {
                data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 0);
            }else {
                try {
                    int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
                    if (value == 1) {
                        player.sendMessage(ChatColor.AQUA + "Armor swap is Disabled");
                        data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 0);
                    }else {
                        player.sendMessage(ChatColor.AQUA + "Armor swap is enabled");
                        data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 1);
                    }
                }catch (Exception value){
                    System.out.println("error message by plugin" + value.getMessage());
                }

            }

        }
        return true;
    }
}
