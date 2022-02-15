package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.ItemEquipExceptionType;
import com.jorgsaa.item.Slot;
import com.jorgsaa.item.armor.Armor;
import com.jorgsaa.item.armor.ArmorType;
import com.jorgsaa.item.armor.InvalidArmorException;
import com.jorgsaa.item.weapon.InvalidWeaponException;
import com.jorgsaa.item.weapon.Weapon;
import com.jorgsaa.item.weapon.WeaponType;

import java.util.HashMap;
import java.util.List;

public abstract class Character {

    private final String name;
    private Integer level = 1;
    private HashMap<Slot, Item> equipment;

    protected Character(String name) {
        this.name = name;
    }

    public abstract PrimaryAttribute getBasePrimaryAttributes();

    public abstract PrimaryAttribute getGainedPrimaryAttributes();

    public abstract List<ArmorType> getValidArmorTypes();

    public abstract List<WeaponType> getValidWeaponTypes();

    protected abstract Double getCharacterDamageMultiplier();

    public PrimaryAttribute getTotalPrimaryAttributes() {
        return getBasePrimaryAttributes()
                .add(getGainedPrimaryAttributes())
                .add(getEquippedArmorAttributes());
    }

    public void equip(Item item) {

        // Check level requirement
        if (item.getRequiredLevel() <= level) {
            if (item instanceof Weapon weapon)
                throw new InvalidWeaponException(ItemEquipExceptionType.LEVEL_INSUFFICIENT, this, weapon);
            else if (item instanceof Armor armor) {
                throw new InvalidArmorException(ItemEquipExceptionType.LEVEL_INSUFFICIENT, this, armor);
            }
        }

        // Check class compatibility with weapon type
        if (item instanceof Weapon weapon && !getValidWeaponTypes().contains(weapon.getType()))
            throw new InvalidWeaponException(ItemEquipExceptionType.CLASS_INCOMPATIBLE, this, weapon);

        // Check class compatibility with armor type
        if (item instanceof Armor armor && !getValidArmorTypes().contains(armor.getType()))
            throw new InvalidArmorException(ItemEquipExceptionType.CLASS_INCOMPATIBLE, this, armor);

        equipment.put(item.getSlot(), item);
    }

    public PrimaryAttribute getEquippedArmorAttributes() {
        return equipment.values().stream()
                .filter(item -> item instanceof Armor) // Filter for Armor typed items
                .map(item -> (Armor) item) // Cast each item to Armor
                .map(Armor::getAttributes) // Map each armor into its attributes
                .reduce(PrimaryAttribute.of(0, 0, 0), PrimaryAttribute::add); // Sum the attributes
    }

    public Double getEquippedWeaponDPS() {
        final Double weaponDPS = equipment.values().stream()
                .filter(item -> item instanceof Weapon) // Filter for Weapon typed items
                .map(item -> (Weapon) item) // Cast each item to Weapon
                .map(Weapon::getDPS) // Map each weapon into DPS
                .reduce(0d, Double::sum); // Sum the DPS

        return weaponDPS != 0 ? weaponDPS : 1;
    }

    public Double getDamage() {
        return getEquippedWeaponDPS() * getCharacterDamageMultiplier();
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
