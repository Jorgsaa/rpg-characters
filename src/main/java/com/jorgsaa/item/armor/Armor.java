package com.jorgsaa.item.armor;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.Slot;

public class Armor extends Item {

    private final ArmorType type;
    private final PrimaryAttribute attributes;

    public Armor(String name, Integer requiredLevel, Slot slot, ArmorType type, PrimaryAttribute attributes) {
        super(name, requiredLevel, slot);
        this.type = type;
        this.attributes = attributes;
    }

    public PrimaryAttribute getAttributes() {
        return attributes;
    }

    public ArmorType getType() {
        return type;
    }

}
