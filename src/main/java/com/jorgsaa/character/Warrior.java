package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.armor.ArmorType;
import com.jorgsaa.item.weapon.WeaponType;

import java.util.List;

import static com.jorgsaa.item.armor.ArmorType.*;
import static com.jorgsaa.item.weapon.WeaponType.*;

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
    public List<ArmorType> getValidArmorTypes() {
        return List.of(MAIL, PLATE);
    }

    @Override
    public List<WeaponType> getValidWeaponTypes() {
        return List.of(AXE, HAMMER, SWORD);
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1 + (0.01d * getTotalPrimaryAttributes().getIntelligence());
    }
}
