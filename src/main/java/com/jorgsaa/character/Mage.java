package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.armor.ArmorType;
import com.jorgsaa.item.weapon.WeaponType;

import java.util.List;

import static com.jorgsaa.item.armor.ArmorType.CLOTH;
import static com.jorgsaa.item.weapon.WeaponType.STAFF;
import static com.jorgsaa.item.weapon.WeaponType.WAND;

public class Mage extends Character {

    public Mage(String name) {
        super(name);
    }

    @Override
    public PrimaryAttribute getBasePrimaryAttributes() {
        return PrimaryAttribute.of(1, 1, 8);
    }

    @Override
    public PrimaryAttribute getGainedPrimaryAttributes() {
        return PrimaryAttribute.of(1, 1, 5).multiply(getLevel() - 1);
    }

    @Override
    public List<ArmorType> getValidArmorTypes() {
        return List.of(CLOTH);
    }

    @Override
    public List<WeaponType> getValidWeaponTypes() {
        return List.of(STAFF, WAND);
    }

    @Override
    protected Double getCharacterDamageMultiplier() {
        return 1 + (0.01d * getTotalPrimaryAttributes().intelligence());
    }

}
