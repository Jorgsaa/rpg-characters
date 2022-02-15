package com.jorgsaa.item.weapon;

import com.jorgsaa.character.Character;
import com.jorgsaa.item.ItemEquipExceptionType;

public class InvalidWeaponException extends RuntimeException {
    public InvalidWeaponException(ItemEquipExceptionType type, Character character, Weapon weapon) {
        super(String.format(type.getMessage(), character, weapon));
    }
}
