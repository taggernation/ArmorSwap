package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.Clicked;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class clickEvents implements Listener {
    String sound = ArmorSwap.getPlugin().getConfig().getString("Sound");

    //
    // ARMOR STAND EVENTS STARTS HERE
    //
    @EventHandler
    public void onArmorStandRightClickEvent(PlayerInteractAtEntityEvent event) {
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Armor_stand_swap");
        if (isEnabled && !event.isCancelled()) {
            Player player = event.getPlayer();

            int value = getArmorSwapEnabled(player);
            if (value == 1) {

                if (!player.isSneaking()) return;
                Entity entity = event.getRightClicked();

                if (entity.getType().equals(EntityType.ARMOR_STAND)) {

                    event.setCancelled(true);
                    ArmorStand stand = (ArmorStand) entity;
                    if (stand.getEquipment() == null) return;
                    if (!stand.isVisible()) return;

                    ItemStack standBoots = Objects.requireNonNull(stand.getEquipment()).getBoots();
                    ItemStack standLeggings = Objects.requireNonNull(stand.getEquipment()).getLeggings();
                    ItemStack standChestplate = Objects.requireNonNull(stand.getEquipment()).getChestplate();
                    ItemStack standHelmet = Objects.requireNonNull(stand.getEquipment()).getHelmet();
                    ItemStack standMainHand = Objects.requireNonNull(stand.getEquipment().getItemInMainHand());
                    ItemStack standOffHand = Objects.requireNonNull(stand.getEquipment().getItemInOffHand());
                    // player

                    ItemStack playerBoots = Objects.requireNonNull(player.getEquipment()).getBoots();
                    ItemStack playerLeggings = Objects.requireNonNull(player.getEquipment()).getLeggings();
                    ItemStack playerChestplate = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ItemStack playerHelmet = Objects.requireNonNull(player.getEquipment()).getHelmet();
                    ItemStack playerMainHand = Objects.requireNonNull(player.getEquipment().getItemInMainHand());
                    ItemStack playerOffHand = Objects.requireNonNull(player.getEquipment().getItemInOffHand());
                    // armor stand set armor
                    if (CanMove(standBoots) || CanMove(playerBoots)) {
                        Objects.requireNonNull(stand.getEquipment()).setBoots(playerBoots);
                        player.getInventory().setBoots(standBoots);
                    }
                    if (CanMove(standLeggings) || CanMove(playerLeggings)) {
                        stand.getEquipment().setLeggings(playerLeggings);
                        player.getInventory().setLeggings(standLeggings);
                    }
                    if (CanMove(standChestplate) || CanMove(playerChestplate)) {
                        stand.getEquipment().setChestplate(playerChestplate);
                        player.getInventory().setChestplate(standChestplate);
                    }
                    if (CanMove(standHelmet) || CanMove(playerHelmet)) {
                        stand.getEquipment().setHelmet(playerHelmet);
                        player.getInventory().setHelmet(standHelmet);
                    }
                    if (stand.hasArms()) {
                        if (CanMove(standMainHand) || CanMove(playerMainHand)) {
                            stand.getEquipment().setItemInMainHand(playerMainHand);
                            player.getEquipment().setItemInMainHand(standMainHand);
                        }
                        if (CanMove(standOffHand) || CanMove(playerOffHand)) {
                            stand.getEquipment().setItemInOffHand(playerOffHand);
                            player.getEquipment().setItemInOffHand(standOffHand);
                        }
                    }

                    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);

                }
            }
        }
    }

    public boolean CanMove(ItemStack item) {
        if (item == null) return true;
        if (item.getType().equals(Material.AIR)) return true;
        return !item.containsEnchantment(Enchantment.BINDING_CURSE);
    }

    //
    // ARMOR STAND EVENT ENDS HERE
    //
    //
    // ITEM FRAME EVENT STARTS HERE
    //
    @EventHandler
    public void onItemFrameRightClickEvent(PlayerInteractEntityEvent event) {
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Item_frame_swap");
        if (isEnabled && !event.isCancelled()) {
            Player player = event.getPlayer();
            int value = getArmorSwapEnabled(player);
            if (value == 1) {
                if (!player.isSneaking()) return;
                Entity entity = event.getRightClicked();
                // Item frame or glow item frame
                if (entity.getType().equals(EntityType.ITEM_FRAME)
                        ||
                        entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
                    event.setCancelled(true);
                    ItemFrame clickedFrame = (ItemFrame) entity;
                    ItemStack playerItem = player.getInventory().getItemInMainHand();
                    ItemStack itemOnFrame = clickedFrame.getItem();

                    if (playerItem.getAmount() == 1) {
                        clickedFrame.setItem(playerItem);
                        player.getInventory().setItemInMainHand(itemOnFrame);
                    } else {
                        if (!player.getInventory().contains(Material.AIR)) {
                            ItemStack[] items = player.getInventory().getContents();
                            for (ItemStack item : items) {
                                if (item.getType() == itemOnFrame.getType()) {
                                    if (item.getAmount() <= item.getMaxStackSize()) {
                                        giveItem(playerItem, itemOnFrame, clickedFrame, player);
                                        return;
                                    }
                                }
                            }
                            player.sendMessage(ChatColor.RED + "Inventory full");
                            return;
                        }
                        giveItem(playerItem, itemOnFrame, clickedFrame, player);
                    }
                }
            }
        }
    }

    public void giveItem(ItemStack playerItem, ItemStack itemOnFrame, ItemFrame clickedFrame, Player player) {
        playerItem.setAmount(playerItem.getAmount() - 1);
        clickedFrame.setItem(playerItem);
        player.getInventory().addItem(itemOnFrame);

    }

    //
    // ITEM FRAME EVENT ENDS HERE
    //
    //
    // RIGHT CLICK ON ARMOR EVENT START HERE
    //
    @EventHandler
    public void onArmorRightClickEvent(PlayerInteractEvent event) {
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Main_hand_swap");
        if (isEnabled) {
            Player player = event.getPlayer();
            int value = getArmorSwapEnabled(player);
            if (value == 1) {
                Block block = event.getClickedBlock();
                if (block != null) {
                    if (!block.getType().isInteractable()) {
                        ItemStack Item = player.getInventory().getItemInMainHand();
                        Clicked.playerItem(player, Item, sound);
                    }
                }else {
                    ItemStack Item = player.getInventory().getItemInMainHand();
                    Clicked.playerItem(player, Item, sound);
                }
            }
        }
    }
        //
        // RIGHT CLICK ON ARMOR EVENT ENDS HERE
        //
    public int getArmorSwapEnabled(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return Objects.requireNonNull(
                data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
    }
}