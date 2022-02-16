package com.jorgsaa.character;

import com.jorgsaa.attribute.PrimaryAttribute;
import com.jorgsaa.item.Item;
import com.jorgsaa.item.Slot;
import com.jorgsaa.item.armor.Armor;
import com.jorgsaa.item.armor.ArmorType;
import com.jorgsaa.item.armor.InvalidArmorException;
import com.jorgsaa.item.weapon.InvalidWeaponException;
import com.jorgsaa.item.weapon.Weapon;
import com.jorgsaa.item.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MageTest {

    private Mage mage;

    private static final String ORIGINAL_NAME = "Mage";

    @BeforeEach
    public void setUp() {
        // Arrange
        mage = new Mage(ORIGINAL_NAME);
    }

    @Test
    void getBasePrimaryAttributes_ReturnsNonNullValues() {
        // Arrange
        final PrimaryAttribute attribs = mage.getBasePrimaryAttributes();

        // Assert
        assertThat(
                List.of(attribs.getStrength(), attribs.getDexterity(), attribs.getIntelligence()),
                not(hasItem((Integer) null))
        );
    }

    @Test
    void getGainedPrimaryAttributes_AfterLevelUp_ReturnsChangedValues() {
        // Arrange
        final PrimaryAttribute before = mage.getGainedPrimaryAttributes();

        // Act
        mage.levelUp();
        final PrimaryAttribute after = mage.getGainedPrimaryAttributes();

        // Assert
        assertThat(
                List.of(before.getStrength(), before.getDexterity(), before.getIntelligence()),
                not(contains(after.getStrength(), after.getDexterity(), after.getIntelligence()))
        );
    }

    @Test
    void getValidArmorTypes_WhenArmorValid_ShouldEquipArmor() {
        // Arrange
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 2, 8));

        // Act
        mage.equip(armor);
        final Item equippedArmor = mage.getEquipment(armor.getSlot());

        // Assert
        assertEquals(armor, equippedArmor);
    }

    @Test
    void getValidArmorTypes_WhenArmorTypeIncompatible_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Assert
        assertThrows(InvalidArmorException.class, () -> mage.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark wizard robe", 50, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 20, 100));

        // Assert
        assertThrows(InvalidArmorException.class, () -> mage.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficientAndIncompatibleType_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark steel platebody", 50, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Assert
        assertThrows(InvalidArmorException.class, () -> mage.equip(armor));
    }

    @Test
    void getValidWeaponTypes_WhenWeaponValid_ShouldEquipWeapon() {
        // Arrange
        Weapon weapon = new Weapon("Wizard wand", 0, WeaponType.WAND, 14d, 0.7);

        // Act
        mage.equip(weapon);
        final Item equippedWeapon = mage.getEquipment(Slot.WEAPON);

        // Assert
        assertEquals(weapon, equippedWeapon);
    }

    @Test
    void getValidWeaponType_WhenWeaponTypeIncompatible_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> mage.equip(weapon));
    }

    @Test
    void getValidWeaponType_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Dark steel axe", 50, WeaponType.AXE, 100d, 1.4d);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> mage.equip(weapon));
    }

    //TODO: DPS tests
}
