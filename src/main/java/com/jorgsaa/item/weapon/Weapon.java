package com.jorgsaa.item.weapon;

import com.jorgsaa.item.Item;
import com.jorgsaa.item.Slot;

public class Weapon extends Item {

    private final WeaponType type;
    private final Double damage;
    private final Double attackSpeed;

    public Weapon(String name, Integer requiredLevel, WeaponType type, Double damage, Double attackSpeed) {
        super(name, requiredLevel, Slot.WEAPON);
        this.type = type;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public Double getDPS() {
        return attackSpeed * damage;
    }

    public WeaponType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s (Level %d) [ Damage: %3f Attack Speed: %3f ]", getName(), getRequiredLevel(), damage, attackSpeed);
    }

}
