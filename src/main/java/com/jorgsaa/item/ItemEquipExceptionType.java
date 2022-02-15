package com.jorgsaa.item;

public enum ItemEquipExceptionType {
    LEVEL_INSUFFICIENT("%s does not have the required level to equip %s"),
    CLASS_INCOMPATIBLE("Class of %s is incompatible with the item %s");

    private final String message;

    ItemEquipExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
