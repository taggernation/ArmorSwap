package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
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
    //
    // ARMOR STAND EVENTS STARTS HERE
    //
    @EventHandler
    public void onArmorStandRightClickEvent(PlayerInteractAtEntityEvent event) {
        String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
        boolean isEnabled = ArmorSwap.getPlugin().getConfig().getBoolean("Armor_stand_swap");
        if (isEnabled && !event.isCancelled()){
            Player player = event.getPlayer();
            PersistentDataContainer data = player.getPersistentDataContainer();
            int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {
                if (!player.isSneaking()) return;
                Entity entity = event.getRightClicked();
                if (entity.getType().equals(EntityType.ARMOR_STAND)){
                    event.setCancelled(true);
                    ArmorStand stand = (ArmorStand) entity;
                    if (stand.isInvisible()) return;
                    // armor stand
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
                    if (CanMove(playerBoots)) {
                        stand.getEquipment().setBoots(playerBoots);
                        player.getEquipment().setBoots(standBoots);
                    }
                    if (CanMove(playerLeggings)) {
                        stand.getEquipment().setLeggings(playerLeggings);
                        player.getEquipment().setLeggings(standLeggings);
                    }
                    if (CanMove(playerChestplate)) {
                        stand.getEquipment().setChestplate(playerChestplate);
                        player.getEquipment().setChestplate(standChestplate);
                    }
                    if (CanMove(playerHelmet)) {
                        stand.getEquipment().setHelmet(playerHelmet);
                        player.getEquipment().setHelmet(standHelmet);
                    }
                    if (stand.hasArms()) {
                        stand.getEquipment().setItemInMainHand(playerMainHand);
                        stand.getEquipment().setItemInOffHand(playerOffHand);
                        player.getEquipment().setItemInMainHand(standMainHand);
                        player.getEquipment().setItemInOffHand(standOffHand);
                    }

                    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);

                }
            }
        }
    }

    boolean CanMove(ItemStack item){
        if(item == null) return true;
        if(item.getType().equals(Material.AIR)) return true;
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
            int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {
                if (!player.isSneaking()) return;
                Entity entity = event.getRightClicked();
                // Item frame or glow item frame
                if (entity.getType().equals(EntityType.ITEM_FRAME) || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
                    event.setCancelled(true);
                    ItemFrame clickedFrame = (ItemFrame) entity;
                    ItemStack itemOnFrame = clickedFrame.getItem();
                    ItemStack playerItem = player.getInventory().getItemInMainHand();
                    clickedFrame.setItem(playerItem);
                    player.getInventory().setItemInMainHand(itemOnFrame);
                }
            }
        }
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
        String sound = Objects.requireNonNull(ArmorSwap.getPlugin().getConfig().getString("Sound"));
        if (isEnabled) {
            Player player = event.getPlayer();
            PersistentDataContainer data = player.getPersistentDataContainer();
            int value = Objects.requireNonNull(data.get(new NamespacedKey(ArmorSwap.getPlugin(), "ArmorSwapEnabled"), PersistentDataType.INTEGER));
            if (value == 1) {
                if (Objects.equals(event.getItem(), player.getInventory().getItemInMainHand())) {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK && !Objects.requireNonNull(event.getClickedBlock()).getType().isInteractable()) {
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
    //
    // RIGHT CLICK ON ARMOR EVENT ENDS HERE
    //
}
