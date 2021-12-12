package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.utils.Clicked;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class clickEvents implements Listener {
    String sound = ArmorSwap.getPlugin().getConfig().getString( "Sound");
    //
    // ARMOR STAND EVENTS STARTS HERE
    //
    @EventHandler
    public void onArmorStandRightClickEvent(PlayerInteractAtEntityEvent event) {
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Armor_stand_swap");
        if (isEnabled && !event.isCancelled()){
            Player player = event.getPlayer();

            PersistentDataContainer data = player.getPersistentDataContainer();

            int value = Objects.requireNonNull(data
                    .get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {

                if (!player.isSneaking()) return;
                Entity entity = event.getRightClicked();

                if (entity.getType().equals(EntityType.ARMOR_STAND)){

                    event.setCancelled(true);
                    ArmorStand stand = (ArmorStand) entity;
                    if (!stand.isVisible()) return;

                    // armor stand set armor
                    if (CanMove(playerTool(player, getType.boots))) {
                        Objects.requireNonNull(stand.getEquipment())
                                .setBoots(playerTool(player, getType.boots));
                        Objects.requireNonNull(player.getEquipment())
                                .setBoots(playerTool(stand, getType.boots));
                    }
                    if (CanMove(playerTool(player, getType.leggings))) {
                        Objects.requireNonNull(stand.getEquipment())
                                .setLeggings(playerTool(player, getType.leggings));
                        Objects.requireNonNull(player.getEquipment())
                                .setLeggings(playerTool(stand, getType.leggings));
                    }
                    if (CanMove(playerTool(player, getType.chestPlate))) {
                        Objects.requireNonNull(stand.getEquipment())
                                .setChestplate(playerTool(player, getType.chestPlate));
                        Objects.requireNonNull(player.getEquipment())
                                .setChestplate(playerTool(stand, getType.chestPlate));
                    }
                    if (CanMove(playerTool(player, getType.helmet))) {
                        Objects.requireNonNull(stand.getEquipment())
                                .setHelmet(playerTool(player, getType.helmet));
                        Objects.requireNonNull(player.getEquipment())
                                .setHelmet(playerTool(stand, getType.helmet));
                    }
                    if (stand.hasArms()) {
                        Objects.requireNonNull(stand.getEquipment())
                                .setItemInMainHand(playerTool(player, getType.mainHand));
                        stand.getEquipment().setItemInOffHand(playerTool(player, getType.offHand));
                        Objects.requireNonNull(player.getEquipment())
                                .setItemInMainHand(playerTool(stand, getType.mainHand));
                        player.getEquipment().setItemInOffHand(playerTool(stand, getType.offHand));
                    }

                    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);

                }
            }
        }
    }

    ItemStack playerTool(LivingEntity entity, getType type) {
        return switch (type) {
            case boots -> Objects.requireNonNull(entity.getEquipment()).getBoots();
            case leggings -> Objects.requireNonNull(entity.getEquipment()).getLeggings();
            case chestPlate -> Objects.requireNonNull(entity.getEquipment()).getChestplate();
            case helmet -> Objects.requireNonNull(entity.getEquipment()).getHelmet();
            case mainHand -> Objects.requireNonNull(entity.getEquipment()).getItemInMainHand();
            case offHand -> Objects.requireNonNull(entity.getEquipment()).getItemInOffHand();
        };
    }
    public enum getType{
        boots, leggings, chestPlate, helmet, mainHand, offHand
    }
    boolean CanMove(ItemStack item){
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
            PersistentDataContainer data = player.getPersistentDataContainer();
            int value = Objects.requireNonNull(data
                    .get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
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
                    }else {
                        if (!player.getInventory().contains(Material.AIR)) {
                            ItemStack[] items = player.getInventory().getContents();
                            for (ItemStack item: items) {
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
            PersistentDataContainer data = player.getPersistentDataContainer();
            int value = Objects.requireNonNull(data
                    .get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {

                if (event.getAction()
                        ==
                        Action.RIGHT_CLICK_AIR
                        ||
                        event.getAction()
                                ==
                                Action.RIGHT_CLICK_BLOCK
                                &&
                                !Objects.requireNonNull(event.getClickedBlock())
                                        .getType().isInteractable())
                {

                    ItemStack Item = player.getInventory().getItemInMainHand();
                    Clicked.playerItem(player, Item, sound);
                }
            }
        }
    }
    //
    // RIGHT CLICK ON ARMOR EVENT ENDS HERE
    //
}
