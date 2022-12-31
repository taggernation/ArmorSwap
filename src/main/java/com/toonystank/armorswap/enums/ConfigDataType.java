package com.toonystank.armorswap.enums;

import com.toonystank.armorswap.ArmorSwap;

public enum ConfigDataType {

    ARMOR_STAND_SWAP("Armor_stand_swap", true),
    ARMOR_STAND_SWAP_PERMISSION("Armor_Stand_Swap_Permission"),
    ITEM_FRAME_SWAP("Item_frame_swap", true),
    ITEM_FRAME_SWAP_PERMISSION("Item_Frame_Swap_Permission"),
    MAIN_HAND_SWAP("Main_hand_swap", true),
    MAIN_HAND_SWAP_PERMISSION("Main_Hand_Swap_Permission"),
    SOUND("Sound"),
    ENABLE("Enable"),
    DISABLE("Disable"),
    WRONG_USAGE("usageError"),
    BYPASS_PERMISSION("Bypass_permission"),
    BYPASS_ENCHANTMENT("Bypass_enchantment",true),
    VERSION("Version"),
    PREFIX("prefix");

    private final String name;
    private Boolean value;

    ConfigDataType(String name) {
        this(name, false);
    }
    ConfigDataType(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public String getString() {
        return ArmorSwap.getPlugin().getConfigData().getMessage(name);
    }
    public boolean getBoolean() {
        if (value = true) {
            return ArmorSwap.getPlugin().getConfigData().getBoolean(name);
        }
        return false;
    }
}
