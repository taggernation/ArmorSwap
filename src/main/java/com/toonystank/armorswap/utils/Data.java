package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.ArmorSwap;

public class Data {

    public boolean getBoolean(DataType type) {
        switch (type) {
            case ARMOR_STAND_SWAP -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Armor_stand_swap");
            }
            case MAIN_HAND_SWAP -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Main_hand_swap");
            }
            case ITEM_FRAME_SWAP -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Item_frame_swap");
            }
        }
        return false;
    }
}
