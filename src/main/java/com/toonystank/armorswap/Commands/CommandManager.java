package com.toonystank.armorswap.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.enums.ConfigDataType;
import com.toonystank.armorswap.utils.ConfigData;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Objects;

@CommandAlias("Armorswap|armorswap|as")
public class CommandManager extends BaseCommand {
  private final String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
  private ConfigData configData;
  private Plugin plugin;

  public CommandManager(ConfigData configData, Plugin plugin) {
    this.configData = configData;
    this.plugin = plugin;
  }

  @Subcommand("reload")
  @CommandPermission("armorswap.command.reload")
  @Description("Reloads ArmorSwap plugin")
  public void onReload(CommandSender sender) throws IOException, InvalidConfigurationException {
    if (sender instanceof Player player) {
      configData.reload();
      player.sendMessage(ConfigDataType.PREFIX.getString() + "" + ChatColor.AQUA + " ArmorSwap is reloaded");
    } else {
      plugin.reloadConfig();
      plugin.getLogger().info(ConfigDataType.PREFIX.getString() + "" + ChatColor.AQUA + " ArmorSwap is reloaded");
    }
  }

  @Subcommand("head")
  @CommandPermission("armorswap.command.head")
  @Description("Get head, since you can't get it irl ;)")
  public void onHead(CommandSender sender) {
    if (sender instanceof Player player) {
      ItemStack itemForHead = player.getInventory().getItemInMainHand();
      ItemStack itemForHand = player.getInventory().getHelmet();
      player.getInventory().setHelmet(itemForHead);
      player.getInventory().setItemInMainHand(itemForHand);
      player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
    } else {
      plugin.getLogger().info( ConfigDataType.PREFIX.getString() + "" + ChatColor.RED + " Must be an player to run this command.");
    }
  }

  @Subcommand("help")
  @Description("Help command for plugin")
  public void onHelp(CommandSender sender) {
    if (sender instanceof Player player) {
      player.sendMessage(ChatColor.AQUA + "" + ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "|              |" + "\n" + "\n" + ChatColor.YELLOW + "* /Armorswap reload - Reloads the plugin" + "\n" + "* /Armorswap head - Replace player head with holding item" + "\n" + "\n" + ChatColor.AQUA + "" + ChatColor.RESET + "" + "|              |");
    } else {
      plugin.getLogger().info(ChatColor.AQUA + "" + ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "|              |" + "\n" + "\n" + ChatColor.YELLOW + "* /Armorswap reload - Reloads the plugin" + "\n" + "* /Armorswap head - Replace player head with holding item" + "\n" + "\n" + ChatColor.AQUA + "" + ChatColor.RESET + "" + "|              |");
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
            player.sendMessage(ConfigDataType.DISABLE.getString());
            data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 0);
          }else {
            player.sendMessage(ConfigDataType.ENABLE.getString());
            data.set(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER, 1);
          }
        }
        catch (Exception value){
          System.out.println(value.getMessage());
        }
      }
    } else {
      plugin.getLogger().info(ConfigDataType.PREFIX.getString() + "" + ChatColor.RED + " Must be an player to run this command.");
    }
  }
}
