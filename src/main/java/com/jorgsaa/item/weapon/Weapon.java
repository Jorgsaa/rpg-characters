package com.jorgsaa.item.weapon;

import com.jorgsaa.item.Item;
import com.jorgsaa.item.Slot;

public class Weapon extends Item {

    private final Double damage;
    private final Double attackSpeed;

    protected Weapon(String name, Integer requiredLevel, Double damage, Double attackSpeed) {
        super(name, requiredLevel, Slot.WEAPON);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

}
