package com.toonystank.armorswap.enums;

public enum ConfigDataType {
    ARMOR_STAND_SWAP("Armor_stand_swap"),
    ITEM_FRAME_SWAP("Item_frame_swap"),
    MAIN_HAND_SWAP("Main_hand_swap"),
    SOUND("Sound"),
    ENABLE("Enable"),
    DISABLE("Disable"),
    WRONG_USAGE("usageError"),
    BYPASS_ENCHANTMENT("Bypass_enchantment"),
    PREFIX("prefix");

    private final String name;

    ConfigDataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
