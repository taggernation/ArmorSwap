package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
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
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Main_hand_swap");
        String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
        if (isEnabled) {
            Player player = event.getPlayer();
            PersistentDataContainer data = player.getPersistentDataContainer();
            int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {
                if (Objects.equals(event.getItem(), player.getInventory().getItemInMainHand())) {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                        ItemStack Item = player.getInventory().getItemInMainHand();

                        if (Item.getType().toString().toLowerCase().contains("helmet")) {

                            ItemStack returnItem = Objects.requireNonNull(player.getInventory().getHelmet());
                            if (returnItem.containsEnchantment(Enchantment.BINDING_CURSE)) return;
                            player.getInventory().setItemInMainHand(returnItem);
                            player.getInventory().setHelmet(Item);
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                        }
                        else if (Item.getType().toString().toLowerCase().contains("chestplate")) {

                            ItemStack returnItem = Objects.requireNonNull(player.getInventory().getChestplate());
                            if (returnItem.containsEnchantment(Enchantment.BINDING_CURSE)) return;
                            player.getInventory().setItemInMainHand(returnItem);
                            player.getInventory().setChestplate(Item);
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                        }
                        else if (Item.getType().toString().toLowerCase().contains("boots")) {

                            ItemStack returnItem = Objects.requireNonNull(player.getInventory().getBoots());
                            if (returnItem.containsEnchantment(Enchantment.BINDING_CURSE)) return;
                            player.getInventory().setItemInMainHand(returnItem);
                            player.getInventory().setBoots(Item);
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                        }
                        else if (Item.getType().toString().toLowerCase().contains("leggings")) {

                            ItemStack returnItem = Objects.requireNonNull(player.getInventory().getLeggings());
                            if (returnItem.containsEnchantment(Enchantment.BINDING_CURSE)) return;
                            player.getInventory().setItemInMainHand(returnItem);
                            player.getInventory().setLeggings(Item);
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }
}