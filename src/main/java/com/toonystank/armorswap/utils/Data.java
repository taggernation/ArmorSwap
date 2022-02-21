package com.toonystank.armorswap.utils;

import com.toonystank.armorswap.ArmorSwap;

public class Data {

    public boolean getBoolean(DataType type) {
        switch (type) {
            case Armor_stand_swap -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Armor_stand_swap");
            }
            case Main_hand_swap -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Main_hand_swap");
            }
            case Item_frame_swap -> {
                return ArmorSwap.getPlugin().getConfig().getBoolean("Item_frame_swap");
            }
        }
        return false;
    }
}
