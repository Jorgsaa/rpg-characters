package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Warrior extends Character {
    public Warrior(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return null;
    }

    @Override
    public PrimaryAttribute getTotalPrimaryAttributes() {
        return null;
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return null;
    }
}
