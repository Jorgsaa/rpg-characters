package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Rogue extends Character {
    public Rogue(String name) {
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
    protected Double getCharacterDamage() {
        return null;
    }
}
