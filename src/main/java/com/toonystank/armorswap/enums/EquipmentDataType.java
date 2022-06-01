package com.toonystank.armorswap.enums;

public enum EquipmentDataType {

    PLAYER_BOOTS("BOOTS"),
    PLAYER_LEGGINGS("LEGGINGS"),
    PLAYER_CHEST_PLATE("CHESTPLATE"),
    PLAYER_ELYTRA("ELYTRA"),
    PLAYER_HELMET("HELMET"),
    STAND_BOOTS("BOOTS"),
    STAND_LEGGINGS("LEGGINGS"),
    STAND_CHEST_PLATE("CHESTPLATE"),
    STAND_ELYTRA("ELYTRA"),
    STAND_HELMET("HELMET"),
    PLAYER_MAIN_HAND("MAIN_HAND"),
    PLAYER_OFF_HAND("OFF_HAND"),
    STAND_MAIN_HAND("MAIN_HAND"),
    STAND_OFF_HAND("OFF_HAND");

    private final String name;

    EquipmentDataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public boolean contains(String name) {
        return this.name.contains(name);
    }
}