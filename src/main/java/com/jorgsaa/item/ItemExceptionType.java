package com.jorgsaa.item;

public enum ItemExceptionType {
    LEVEL_INSUFFICIENT("%s does not have the required level to equip %s"),
    CLASS_INCOMPATIBLE("Class of %s is incompatible with the item %s"),
    SLOT_INCOMPATIBLE("Item cannot be constructed with the slot %s");

    private final String message;

    ItemExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
