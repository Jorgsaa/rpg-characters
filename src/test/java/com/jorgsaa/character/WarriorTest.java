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

class WarriorTest {

    private static final String ORIGINAL_NAME = "Warrior";
    private Warrior warrior;

    @BeforeEach
    public void setUp() {
        // Arrange
        warrior = new Warrior(ORIGINAL_NAME);
    }

    @Test
    void getName_ReturnsOriginalValue() {
        // Assert
        assertEquals(ORIGINAL_NAME, warrior.getName());
    }

    @Test
    void getBasePrimaryAttributes_ReturnsNonNullValues() {
        // Arrange
        final PrimaryAttribute attribs = warrior.getBasePrimaryAttributes();

        // Assert
        assertThat(
                List.of(attribs.strength(), attribs.dexterity(), attribs.intelligence()),
                not(hasItem((Integer) null))
        );
    }

    @Test
    void getGainedPrimaryAttributes_AfterLevelUp_ReturnsChangedValues() {
        // Arrange
        final PrimaryAttribute before = warrior.getGainedPrimaryAttributes();

        // Act
        warrior.levelUp();
        final PrimaryAttribute after = warrior.getGainedPrimaryAttributes();

        // Assert
        assertThat(
                List.of(before.strength(), before.dexterity(), before.intelligence()),
                not(contains(after.strength(), after.dexterity(), after.intelligence()))
        );
    }

    @Test
    void getValidArmorTypes_WhenArmorValid_ShouldEquipArmor() {
        // Arrange
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        warrior.equip(armor);
        final Item equippedArmor = warrior.getEquipment(armor.getSlot());

        // Assert
        assertEquals(armor, equippedArmor);
    }

    @Test
    void getValidArmorTypes_WhenArmorTypeIncompatible_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 2, 8));

        // Act
        warrior.setLevel(10);

        // Assert
        assertThrows(InvalidArmorException.class, () -> warrior.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark steel platebody", 50, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        warrior.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> warrior.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficientAndIncompatibleType_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark wizard robe", 50, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 20, 100));

        // Act
        warrior.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> warrior.equip(armor));
    }

    @Test
    void getValidWeaponTypes_WhenWeaponValid_ShouldEquipWeapon() {
        // Arrange
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);

        // Act
        warrior.equip(weapon);
        final Item equippedWeapon = warrior.getEquipment(Slot.WEAPON);

        // Assert
        assertEquals(weapon, equippedWeapon);
    }

    @Test
    void getValidWeaponType_WhenWeaponTypeIncompatible_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Wizard wand", 0, WeaponType.WAND, 14d, 0.7);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> warrior.equip(weapon));
    }

    @Test
    void getValidWeaponType_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Dark steel axe", 50, WeaponType.AXE, 100d, 1.4d);

        // Act
        warrior.setLevel(49);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> warrior.equip(weapon));
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndNoEquipment_EqualsBaseAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = warrior.getBasePrimaryAttributes();

        // Act
        warrior.setLevel(1);

        // Assert
        assertEquals(baseAttribs, warrior.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel2AndNoEquipment_EqualsSumOfBaseAndGainedAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = warrior.getBasePrimaryAttributes();

        // Act
        warrior.setLevel(2);
        PrimaryAttribute gainedAttribs = warrior.getGainedPrimaryAttributes();

        // Assert
        assertEquals(baseAttribs.add(gainedAttribs), warrior.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndArmor_EqualsSumOfBaseAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));
        PrimaryAttribute baseAttribs = warrior.getBasePrimaryAttributes();
        warrior.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = warrior.getEquippedArmorAttributes();

        // Assert
        assertEquals(baseAttribs.add(equippedArmorAttributes), warrior.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel10AndArmor_EqualsSumOfBaseAndGainedAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));
        PrimaryAttribute baseAttribs = warrior.getBasePrimaryAttributes();
        PrimaryAttribute gainedAttribs = warrior.getGainedPrimaryAttributes();
        warrior.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = warrior.getEquippedArmorAttributes();

        // Assert
        assertEquals(
                baseAttribs
                        .add(equippedArmorAttributes)
                        .add(gainedAttribs),
                warrior.getTotalPrimaryAttributes()
        );
    }

    @Test
    void getGainedPrimaryAttributes_WhenLevel1_ReturnsZeroAttributes() {
        // Act
        warrior.setLevel(1);

        // Assert
        assertEquals(PrimaryAttribute.of(0, 0, 0), warrior.getGainedPrimaryAttributes());
    }

    @Test
    void getDPS_WhenLevel1AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(1);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(1 * (1 + 0.01d * attribs.strength()), dps);
    }

    @Test
    void getDPS_WhenLevel10AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(10);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(1 + (0.01d * attribs.strength()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeapon_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(1);
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);
        warrior.equip(weapon);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.strength()), dps);
    }

    @Test
    void getDps_WhenLevel10AndWeapon_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(10);
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);
        warrior.equip(weapon);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.strength()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);
        warrior.equip(weapon);
        warrior.equip(armor);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.strength()), dps);
    }

    @Test
    void getDps_WhenLevel10AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        warrior.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);
        warrior.equip(weapon);
        warrior.equip(armor);
        PrimaryAttribute attribs = warrior.getTotalPrimaryAttributes();

        // Act
        Double dps = warrior.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.strength()), dps);
    }

}
