package com.jorgsaa.item.armor;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.Slot;

public class Armor extends Item {

    private final PrimaryAttribute attributes;

    protected Armor(String name, Integer requiredLevel, Slot slot, PrimaryAttribute attributes) {
        super(name, requiredLevel, slot);
        this.attributes = attributes;
    }

}
