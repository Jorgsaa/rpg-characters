package com.jorgsaa.item.armor;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArmorTest {

    private Armor armor;

    private static final String ORIGINAL_NAME = "Steel platebody";
    private static final Integer ORIGINAL_LEVEL = 10;
    private static final Slot ORIGINAL_SLOT = Slot.BODY;
    private static final ArmorType ORIGINAL_TYPE = ArmorType.PLATE;
    private static final PrimaryAttribute ORIGINAL_ATTRIBUTES = PrimaryAttribute.of(10, 2, 2);

    @BeforeEach
    public void setUp() {
        armor = new Armor(ORIGINAL_NAME, ORIGINAL_LEVEL, ORIGINAL_SLOT, ORIGINAL_TYPE, ORIGINAL_ATTRIBUTES);
    }

    @Test
    void getAttributes_ReturnsOriginalAttributes() {
        assertEquals(ORIGINAL_ATTRIBUTES, armor.getAttributes());
    }

    @Test
    void getType_ReturnsOriginalType() {
        assertEquals(ORIGINAL_TYPE, armor.getType());
    }

    @Test
    void getName_ReturnsOriginalName() {
        assertEquals(ORIGINAL_NAME, armor.getName());
    }

    @Test
    void getRequiredLevel_ReturnsOriginalRequiredLevel() {
        assertEquals(ORIGINAL_LEVEL, armor.getRequiredLevel());
    }

    @Test
    void getSlot_ReturnsOriginalSlot() {
        assertEquals(ORIGINAL_SLOT, armor.getSlot());
    }

    @Test
    void constructor_WhenSlotIncompatible_ThrowsException() {
        assertThrows(
                InvalidArmorException.class,
                () -> armor = new Armor(ORIGINAL_NAME, 10, Slot.WEAPON, ORIGINAL_TYPE, ORIGINAL_ATTRIBUTES)
        );
    }

}
