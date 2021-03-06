package com.jorgsaa.item.weapon;

import com.jorgsaa.character.Character;
import com.jorgsaa.item.ItemExceptionType;

public class InvalidWeaponException extends RuntimeException {

    public InvalidWeaponException(ItemExceptionType type, Character character, Weapon weapon) {
        super(String.format(type.getMessage(), character, weapon));
    }

}
