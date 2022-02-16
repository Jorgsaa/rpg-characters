package com.jorgsaa.attribute;

public record PrimaryAttribute(Integer strength, Integer dexterity,
                               Integer intelligence) {

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

    public PrimaryAttribute multiply(Integer multiplier) {
        return of(
                strength * multiplier,
                dexterity * multiplier,
                intelligence * multiplier
        );
    }

    @Override
    public String toString() {
        return String.format("[ Strength: %3d Dexterity: %3d Intelligence: %3d ]", strength, dexterity, intelligence);
    }

}
