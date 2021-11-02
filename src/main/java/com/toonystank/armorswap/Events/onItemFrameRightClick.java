package com.toonystank.armorswap.Events;

import com.toonystank.armorswap.ArmorSwap;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class onItemFrameRightClick implements Listener {

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
}
