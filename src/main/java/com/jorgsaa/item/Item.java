package com.jorgsaa.item;

public abstract class Item {

    private final String name;
    private final Integer requiredLevel;
    private final Slot slot;

    protected Item(String name, Integer requiredLevel, Slot slot) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public Slot getSlot() {
        return slot;
    }

}
