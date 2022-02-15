package com.jorgsaa.item.armor;

import com.jorgsaa.character.Character;
import com.jorgsaa.item.ItemEquipExceptionType;

public class InvalidArmorException extends RuntimeException {

    public InvalidArmorException(ItemEquipExceptionType type, Character character, Armor armor) {
        super(String.format(type.getMessage(), character, armor));
    }

}
