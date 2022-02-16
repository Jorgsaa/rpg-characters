package com.jorgsaa.item.armor;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.ItemExceptionType;
import com.jorgsaa.item.Slot;

public class Armor extends Item {

    private final ArmorType type;
    private final PrimaryAttribute attributes;

    public Armor(String name, Integer requiredLevel, Slot slot, ArmorType type, PrimaryAttribute attributes) {
        super(name, requiredLevel, slot);
        if (slot == Slot.WEAPON)
            throw new InvalidArmorException(ItemExceptionType.SLOT_INCOMPATIBLE, slot);
        this.type = type;
        this.attributes = attributes;
    }

    public PrimaryAttribute getAttributes() {
        return attributes;
    }

    public ArmorType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s (Level %d) %s ", getName(), getRequiredLevel(), attributes);
    }

}
