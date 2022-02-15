package com.jorgsaa.item.armor;

import com.jorgsaa.character.Character;
import com.jorgsaa.item.ItemExceptionType;
import com.jorgsaa.item.Slot;

public class InvalidArmorException extends RuntimeException {

    public InvalidArmorException(ItemExceptionType type, Character character, Armor armor) {
        super(String.format(type.getMessage(), character, armor));
    }

    public InvalidArmorException(ItemExceptionType type, Slot slot) {
        super(String.format(type.getMessage(), slot));
    }

}
