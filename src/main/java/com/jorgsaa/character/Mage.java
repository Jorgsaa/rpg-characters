package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public class Mage extends Character {
    public Mage(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return PrimaryAttribute.of(1, 1, 8);
    }

    @Override
    public PrimaryAttribute getTotalPrimaryAttributes() {
        return getBasePrimaryAttributes().add(
                PrimaryAttribute.of(1, 1, 5).multiply(getLevel() - 1)
        );
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1.01d * getTotalPrimaryAttributes().getIntelligence();
    }
}
