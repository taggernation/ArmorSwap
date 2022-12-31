package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import com.toonystank.armorswap.enums.ConfigDataType;
import com.toonystank.armorswap.enums.EquipmentDataType;
import com.toonystank.armorswap.utils.ConfigData;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ClickEvent implements Listener {
    private String sound;
    private final boolean armorStandSwap;
    private final boolean itemFrameSwap;
    private final boolean mainHandSwap;

    private final ConfigData configData;
    private final ArmorSwap plugin;
    public ClickEvent(ConfigData configData, ArmorSwap plugin) throws IOException {
        this.configData = configData;
        this.plugin = plugin;
        this.armorStandSwap = ConfigDataType.ARMOR_STAND_SWAP.getBoolean();
        this.itemFrameSwap = ConfigDataType.ITEM_FRAME_SWAP.getBoolean();
        this.mainHandSwap = ConfigDataType.MAIN_HAND_SWAP.getBoolean();
        this.sound = ConfigDataType.SOUND.getString();
    }

    //
    // ARMOR STAND EVENTS STARTS HERE
    //
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorStandRightClickEvent(PlayerInteractAtEntityEvent event) {
        if (event.isCancelled()) return;
        if (armorStandSwap) {
            Player player = event.getPlayer();
            if (!player.hasPermission(ConfigDataType.ARMOR_STAND_SWAP_PERMISSION.getString())) return;
            int value = getArmorSwapEnabled(player);
            if (!player.isSneaking()) return;
            if (value != 1) return;
            Entity entity = event.getRightClicked();
            if (entity instanceof ArmorStand stand) {
                event.setCancelled(true);
                if (stand.getEquipment() == null) return;
                if (!stand.isVisible()) return;
                List<ItemStack> standArmorList = new ArrayList<>();
                List<ItemStack> playerArmorList = new ArrayList<>();
                for (ItemStack itemStack : stand.getEquipment().getArmorContents()) {
                    if (!canMove(itemStack, player)) return;
                    standArmorList.add(itemStack);
                }
                for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                    if (!canMove(itemStack, player)) return;
                    playerArmorList.add(itemStack);
                }
                player.getInventory().setArmorContents(null);
                player.getInventory().setArmorContents(standArmorList.toArray(ItemStack[]::new));
                stand.getEquipment().setArmorContents(playerArmorList.toArray(ItemStack[]::new));
                if (stand.hasArms()) {
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (canMove(itemStack, player)) return;
                    if (canMove(stand.getEquipment().getItemInMainHand(), player)) return;
                    player.getInventory().setItemInMainHand(stand.getEquipment().getItemInMainHand());
                    stand.getEquipment().setItemInMainHand(itemStack);
                }
                player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
            }
        }
    }
    //
    // ARMOR STAND EVENT ENDS HERE
    //
    //
    // ITEM FRAME EVENT STARTS HERE
    //
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemFrameRightClickEvent(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        if (itemFrameSwap) {
            Player player = event.getPlayer();
            int value = getArmorSwapEnabled(player);
            if (!player.hasPermission(ConfigDataType.ITEM_FRAME_SWAP_PERMISSION.getString())) return;
            if (!player.isSneaking()) return;
            if (value != 1)  return;
            Entity entity = event.getRightClicked();
            // Item frame or glow item frame
            if (entity.getType().equals(EntityType.ITEM_FRAME)
                    ||
                    entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
                event.setCancelled(true);
                ItemFrame clickedFrame = (ItemFrame) entity;
                ItemStack playerItem = player.getInventory().getItemInMainHand();
                ItemStack itemOnFrame = clickedFrame.getItem();

                if (playerItem.getAmount() == 1 && itemOnFrame.getAmount() == 1) {
                    clickedFrame.setItem(playerItem);
                    player.getInventory().setItemInMainHand(itemOnFrame);
                } else {
                    if (!player.getInventory().contains(Material.AIR)) {
                        ItemStack[] items = player.getInventory().getContents();
                        for (ItemStack item : items) {
                            if (item == null) {
                                giveItem(playerItem, itemOnFrame, clickedFrame, player);
                                return;
                            }
                            if (item.getType() == itemOnFrame.getType()) {
                                if (item.getAmount() <= item.getMaxStackSize()) {
                                    giveItem(playerItem, itemOnFrame, clickedFrame, player);
                                    return;
                                }
                            }
                        }
                    }
                    player.sendMessage(ChatColor.RED + "Inventory full");
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
    // RIGHT CLICK with ARMOR EVENT START HERE
    //
    @EventHandler
    public void onArmorRightClickEvent(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (mainHandSwap) {
            boolean success = false;
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().isAir()) return;
            int value = getArmorSwapEnabled(player);
            if (value == 1) {
                Block block = event.getClickedBlock();
                if (block != null) {
                    player.sendMessage(block.getType().toString());
                    if (block.getType().isInteractable()) return;
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                String itemName = item.getType().toString().toUpperCase(Locale.ROOT);
                if (itemName.contains(EquipmentDataType.PLAYER_HELMET.getName())) {
                    if (player.getInventory().getHelmet() == null) return;
                    ItemStack returnItem = player.getInventory().getHelmet();
                    if (canMove(returnItem, player)) return;
                    player.getInventory().setItemInMainHand(returnItem);
                    player.getInventory().setHelmet(item);
                    success = true;
                }
                else if (itemName.contains(EquipmentDataType.PLAYER_CHEST_PLATE.getName()) || itemName.contains(EquipmentDataType.PLAYER_ELYTRA.getName())) {
                    if (player.getInventory().getChestplate() == null) return;
                    ItemStack returnItem = player.getInventory().getChestplate();
                    if (canMove(returnItem, player)) return;
                    player.getInventory().setItemInMainHand(returnItem);
                    player.getInventory().setChestplate(item);
                    success = true;
                }
                else if (itemName.contains(EquipmentDataType.PLAYER_BOOTS.getName())) {
                    if (player.getInventory().getBoots() == null) return;
                    ItemStack returnItem = player.getInventory().getBoots();
                    if (canMove(returnItem, player)) return;
                    player.getInventory().setItemInMainHand(returnItem);
                    player.getInventory().setBoots(item);
                    success = true;
                }
                else if (itemName.contains(EquipmentDataType.PLAYER_LEGGINGS.getName())) {
                    if (player.getInventory().getLeggings() == null) return;
                    ItemStack returnItem = player.getInventory().getLeggings();
                    if (canMove(returnItem, player)) return;
                    player.getInventory().setItemInMainHand(returnItem);
                    player.getInventory().setLeggings(item);
                    success = true;
                }
                if (success) {
                    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
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
    boolean canMove(ItemStack itemStack, Player player) {
        return (itemStack == null || itemStack.containsEnchantment(Enchantment.BINDING_CURSE))
                && !ConfigDataType.BYPASS_ENCHANTMENT.getBoolean()
                && !player.hasPermission(ConfigDataType.BYPASS_PERMISSION.getString());
    }
}