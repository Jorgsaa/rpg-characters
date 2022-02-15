package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Rogue extends Character {
    public Rogue(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return PrimaryAttribute.of(2, 6, 1);
    }

    @Override
    public PrimaryAttribute getTotalPrimaryAttributes() {
        return getBasePrimaryAttributes().add(
                PrimaryAttribute.of(1, 4, 1).multiply(getLevel() - 1)
        );
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1.01d * getTotalPrimaryAttributes().getDexterity();
    }
}
