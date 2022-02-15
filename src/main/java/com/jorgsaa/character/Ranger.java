package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Ranger extends Character {
    public Ranger(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return PrimaryAttribute.of(1, 7, 1);
    }

    @Override
    public PrimaryAttribute getGainedPrimaryAttributes() {
        return PrimaryAttribute.of(1, 5, 1).multiply(getLevel() - 1);
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1.01d * getTotalPrimaryAttributes().getDexterity();
    }
}
