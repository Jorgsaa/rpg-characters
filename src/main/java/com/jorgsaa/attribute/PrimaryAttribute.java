package com.jorgsaa.attribute;

public final class PrimaryAttribute {
    private final Integer strength;
    private final Integer dexterity;
    private final Integer intelligence;

    private PrimaryAttribute(Integer strength, Integer dexterity, Integer intelligence) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public static PrimaryAttribute of(Integer strength, Integer dexterity, Integer intelligence) {
        return new PrimaryAttribute(strength, dexterity, intelligence);
    }

    public PrimaryAttribute add(PrimaryAttribute other) {
        return of(
                strength + other.strength,
                dexterity + other.dexterity,
                intelligence + other.intelligence
        );
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public Integer getIntelligence() {
        return intelligence;
    }
}
