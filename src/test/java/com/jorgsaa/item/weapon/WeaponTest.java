package com.jorgsaa.item.weapon;

import com.jorgsaa.item.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeaponTest {

    private static final String ORIGINAL_NAME = "Steel axe";
    private static final int ORIGINAL_LEVEL = 10;
    private static final WeaponType ORIGINAL_TYPE = WeaponType.AXE;
    private static final double ORIGINAL_DAMAGE = 10d;
    private static final double ORIGINAL_ATTACK_SPEED = 1.3d;

    private Weapon weapon;

    @BeforeEach
    public void setUp() {
        weapon = new Weapon(ORIGINAL_NAME, ORIGINAL_LEVEL, ORIGINAL_TYPE, ORIGINAL_DAMAGE, ORIGINAL_ATTACK_SPEED);
    }

    @Test
    void getDPS_ReturnsCorrectValue() {
        assertEquals(ORIGINAL_DAMAGE * ORIGINAL_ATTACK_SPEED, weapon.getDPS());
    }

    @Test
    void getType_ReturnsOriginalType() {
        assertEquals(ORIGINAL_TYPE, weapon.getType());
    }

    @Test
    void getName_ReturnsOriginalName() {
        assertEquals(ORIGINAL_NAME, weapon.getName());
    }

    @Test
    void getRequiredLevel_ReturnsOriginalRequiredLevel() {
        assertEquals(ORIGINAL_LEVEL, weapon.getRequiredLevel());
    }

    @Test
    void getSlot_ReturnsCorrectSlot() {
        assertEquals(Slot.WEAPON, weapon.getSlot());
    }
}
