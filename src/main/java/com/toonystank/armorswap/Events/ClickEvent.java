package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.Clicked;
import com.toonystank.armorswap.utils.Data;
import com.toonystank.armorswap.utils.DataType;
import com.toonystank.armorswap.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.Objects;

public class ClickEvent implements Listener {
    String sound = ArmorSwap.getPlugin().getConfig().getString("Sound");
    //
    // ARMOR STAND EVENTS STARTS HERE
    //
    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorStandRightClickEvent(PlayerInteractAtEntityEvent event) {
        boolean isEnabled = new Data().getBoolean(DataType.Armor_stand_swap);
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

                    Map<DataType, ItemStack> map = new PlayerData().item;
                    PlayerData.storeData(player, map);
                    PlayerData.storeData(stand, map);
                    // armor stand set armor
                    for (DataType dataType : DataType.values()) {
                        if (!stand.hasArms()) {
                            if (dataType.equals(DataType.Player_MainHand) || dataType.equals(DataType.Player_OffHand) || dataType.equals(DataType.Stand_OffHand) ) continue;
                        }
                        if (PlayerData.canMove(dataType, map)) {
                            PlayerData.setEquipment(dataType, stand, map);
                            PlayerData.setEquipment(dataType ,player, map);

                        }
                    }

                    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);

                }
            }
        }
    }

    //
    // ARMOR STAND EVENT ENDS HERE
    //
    //
    // ITEM FRAME EVENT STARTS HERE
    //
    @EventHandler
    public void onItemFrameRightClickEvent(PlayerInteractEntityEvent event) {
        boolean isEnabled = new Data().getBoolean(DataType.Item_frame_swap);
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
        boolean isEnabled = new Data().getBoolean(DataType.Main_hand_swap);
        if (isEnabled) {
            Player player = event.getPlayer();
            int value = getArmorSwapEnabled(player);
            if (value == 1) {
                Block block = event.getClickedBlock();
                if (block != null) {
                    if (!block.getType().isInteractable()) return;
                }
                ItemStack Item = player.getInventory().getItemInMainHand();
                Clicked.playerItem(player, Item, sound);
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