package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.ItemEquipExceptionType;
import com.jorgsaa.item.Slot;
import com.jorgsaa.item.armor.Armor;
import com.jorgsaa.item.armor.InvalidArmorException;
import com.jorgsaa.item.weapon.InvalidWeaponException;
import com.jorgsaa.item.weapon.Weapon;

import java.util.HashMap;

public abstract class Character {

    private final String name;
    private Integer level = 1;
    private HashMap<Slot, Item> equipment;

    protected Character(String name) {
        this.name = name;
    }

    public abstract PrimaryAttribute getBasePrimaryAttributes();

    public abstract PrimaryAttribute getGainedPrimaryAttributes();

    protected abstract Double getCharacterDamageMultiplier();

    public void equip(Item item) {
        if (item.getRequiredLevel() <= level) {
            if (item instanceof Weapon weapon)
                throw new InvalidWeaponException(ItemEquipExceptionType.LEVEL_INSUFFICIENT, this, weapon);
            else if (item instanceof Armor armor) {
                throw new InvalidArmorException(ItemEquipExceptionType.LEVEL_INSUFFICIENT, this, armor);
            }
        }

        equipment.put(item.getSlot(), item);
    }

    public PrimaryAttribute getEquippedArmorAttributes() {
        return equipment.values().stream()
                .filter(item -> item instanceof Armor) // Filter for Armor typed items
                .map(item -> (Armor) item) // Cast each item to Armor
                .map(Armor::getAttributes) // Map each armor into its attributes
                .reduce(PrimaryAttribute.of(0, 0, 0), PrimaryAttribute::add); // Sum the attributes
    }

    public Double getDamage() {
        return 0d;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }
}
