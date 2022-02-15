package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;

public abstract class Character {

    private final String name;
    private Integer level = 1;

    protected Character(String name) {
        this.name = name;
    }

    public abstract PrimaryAttribute getBasePrimaryAttributes();

    public abstract PrimaryAttribute getTotalPrimaryAttributes();

    protected abstract Double getCharacterDamageMultiplier();

    public Double getDamage() {
        return 0d;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
