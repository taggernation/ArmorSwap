package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class onRightClick implements Listener {

    @EventHandler
    public void onRightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
        if (value == 1) {
            if (Objects.equals(event.getItem(), player.getInventory().getItemInMainHand())) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    ItemStack Item = player.getInventory().getItemInMainHand();
                    if (Item.getType().toString().toLowerCase().contains("helmet")) {
                        ItemStack returnItem = player.getInventory().getHelmet();
                        player.getInventory().setItemInMainHand(returnItem);
                        player.getInventory().setHelmet(Item);
                        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_TURTLE, 1.0F, 1.0F);
                    } else if (Item.getType().toString().toLowerCase().contains("chestplate")) {
                        ItemStack returnItem = player.getInventory().getChestplate();
                        player.getInventory().setItemInMainHand(returnItem);
                        player.getInventory().setChestplate(Item);
                        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_TURTLE, 1.0F, 1.0F);
                    } else if (Item.getType().toString().toLowerCase().contains("boots")) {
                        ItemStack returnItem = player.getInventory().getBoots();
                        player.getInventory().setItemInMainHand(returnItem);
                        player.getInventory().setBoots(Item);
                        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_TURTLE, 1.0F, 1.0F);
                    } else if (Item.getType().toString().toLowerCase().contains("leggings")) {
                        ItemStack returnItem = player.getInventory().getLeggings();
                        player.getInventory().setItemInMainHand(returnItem);
                        player.getInventory().setLeggings(Item);
                        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_TURTLE, 1.0F, 1.0F);
                    } else {
                        player.sendMessage(ChatColor.AQUA + "ItemInHand " + Item);
                    }
                }
            }
        }
    }
}