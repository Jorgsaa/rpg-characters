package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Warrior extends Character {
    public Warrior(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return PrimaryAttribute.of(5, 2, 1);
    }

    @Override
    public PrimaryAttribute getGainedPrimaryAttributes() {
        return PrimaryAttribute.of(3, 2, 1).multiply(getLevel() - 1);
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1.01d * getTotalPrimaryAttributes().getIntelligence();
    }
}
