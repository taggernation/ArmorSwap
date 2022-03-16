package com.toonystank.armorswap.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.getConfigMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@CommandAlias("Armorswap|armorswap|as")
public class CommandManager extends BaseCommand {
  String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));

  @Subcommand("reload")
  @CommandPermission("armorswap.command.reload")
  @Description("Reloads ArmorSwap plugin")
  public void onReload(CommandSender sender) {
    if (sender instanceof Player player) {
      ArmorSwap.getPlugin().reloadConfig();
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.AQUA + " ArmorSwap is reloaded");
    } else {
      ArmorSwap.getPlugin().reloadConfig();
      Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.AQUA + " ArmorSwap is reloaded");
    }
  }

  @Subcommand("head")
  @CommandPermission("armorswap.command.head")
  @Description("Get head, since you can't get it irl ;)")
  public void onHead(CommandSender sender) {
    if (sender instanceof Player player) {
      ItemStack itemForHead = player.getInventory().getItemInMainHand();
      ItemStack itemForHand = Objects.requireNonNull(player.getEquipment()).getHelmet();
      player.getEquipment().setHelmet(itemForHead);
      player.getInventory().setItemInMainHand(itemForHand);
      player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
    } else {
      Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.RED + " Must be an player to run this command.");
    }
  }

  @Subcommand("help")
  @Description("Help command for plugin")
  public void onHelp(CommandSender sender) {
    if (sender instanceof Player player) {
      player.sendMessage(ChatColor.AQUA + "" + ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "|              |" + "\n" + "\n" + ChatColor.YELLOW + "* /Armorswap reload - Reloads the plugin" + "\n" + "* /Armorswap head - Replace player head with holding item" + "\n" + "\n" + ChatColor.AQUA + "" + ChatColor.RESET + "" + "|              |");
    } else {
      Bukkit.getLogger().info(ChatColor.AQUA + "" + ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "|              |" + "\n" + "\n" + ChatColor.YELLOW + "* /Armorswap reload - Reloads the plugin" + "\n" + "* /Armorswap head - Replace player head with holding item" + "\n" + "\n" + ChatColor.AQUA + "" + ChatColor.RESET + "" + "|              |");
    }
  }

  @Default
  @CommandPermission("armorswap.command.use")
  @Description("Enables/Disables armorswap ability.")
  public void onDefault(CommandSender sender) {
    if (sender instanceof Player player) {
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
    } else {
      Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', getConfigMessages.getPrefix()) + "" + ChatColor.RED + " Must be an player to run this command.");
    }
  }
}
