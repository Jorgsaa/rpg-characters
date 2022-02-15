package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.armor.ArmorType;
import com.jorgsaa.item.weapon.WeaponType;

import java.util.List;

import static com.jorgsaa.item.armor.ArmorType.LEATHER;
import static com.jorgsaa.item.armor.ArmorType.MAIL;
import static com.jorgsaa.item.weapon.WeaponType.BOW;

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
    public List<ArmorType> getValidArmorTypes() {
        return List.of(LEATHER, MAIL);
    }

    @Override
    public List<WeaponType> getValidWeaponTypes() {
        return List.of(BOW);
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1 + (0.01d * getTotalPrimaryAttributes().getDexterity());
    }
}
