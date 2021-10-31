package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class onShiftRightClick implements Listener {

    @EventHandler
    public void onShiftRightClickEvent(PlayerInteractAtEntityEvent event) {
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
                stand.getEquipment().setBoots(playerBoots);
                stand.getEquipment().setLeggings(playerLeggings);
                stand.getEquipment().setChestplate(playerChestplate);
                stand.getEquipment().setHelmet(playerHelmet);
                if (stand.hasArms()) {
                    stand.getEquipment().setItemInMainHand(playerMainHand);
                    stand.getEquipment().setItemInOffHand(playerOffHand);
                }

                // player set armor
                player.getEquipment().setBoots(standBoots);
                player.getEquipment().setLeggings(standLeggings);
                player.getEquipment().setChestplate(standChestplate);
                player.getEquipment().setHelmet(standHelmet);
                if (stand.hasArms()) {
                    player.getEquipment().setItemInMainHand(standMainHand);
                    player.getEquipment().setItemInOffHand(standOffHand);
                }

                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_TURTLE, 1.0F, 1.0F);

            }


        }
    }
}

