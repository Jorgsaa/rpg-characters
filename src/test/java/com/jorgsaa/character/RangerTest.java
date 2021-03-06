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

class RangerTest {

    private static final String ORIGINAL_NAME = "Ranger";
    private Ranger ranger;

    @BeforeEach
    public void setUp() {
        // Arrange
        ranger = new Ranger(ORIGINAL_NAME);
    }

    @Test
    void getName_ReturnsOriginalValue() {
        // Assert
        assertEquals(ORIGINAL_NAME, ranger.getName());
    }

    @Test
    void toString_ContainsAttributes() {
        // Act
        String string = ranger.toString();

        // Assert
        assertThat(string, containsString(ranger.getTotalPrimaryAttributes().toString()));
    }

    @Test
    void toString_ContainsEquipment() {
        // Arrange
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, PrimaryAttribute.of(0, 9, 0));
        ranger.equip(armor);

        // Act
        String string = ranger.toString();

        // Assert
        assertThat(string, containsString(armor.toString()));
    }

    @Test
    void getBasePrimaryAttributes_ReturnsNonNullValues() {
        // Arrange
        final PrimaryAttribute attribs = ranger.getBasePrimaryAttributes();

        // Assert
        assertThat(
                List.of(attribs.strength(), attribs.dexterity(), attribs.intelligence()),
                not(hasItem((Integer) null))
        );
    }

    @Test
    void getGainedPrimaryAttributes_AfterLevelUp_ReturnsChangedValues() {
        // Arrange
        final PrimaryAttribute before = ranger.getGainedPrimaryAttributes();

        // Act
        ranger.levelUp();
        final PrimaryAttribute after = ranger.getGainedPrimaryAttributes();

        // Assert
        assertThat(
                List.of(before.strength(), before.dexterity(), before.intelligence()),
                not(contains(after.strength(), after.dexterity(), after.intelligence()))
        );
    }

    @Test
    void getValidArmorTypes_WhenArmorValid_ShouldEquipArmor() {
        // Arrange
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, PrimaryAttribute.of(0, 9, 0));

        // Act
        ranger.equip(armor);
        final Item equippedArmor = ranger.getEquipment(armor.getSlot());

        // Assert
        assertEquals(armor, equippedArmor);
    }

    @Test
    void getValidArmorTypes_WhenArmorTypeIncompatible_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        ranger.setLevel(10);

        // Assert
        assertThrows(InvalidArmorException.class, () -> ranger.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dragonhide body", 50, Slot.BODY, ArmorType.LEATHER, PrimaryAttribute.of(0, 20, 100));

        // Act
        ranger.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> ranger.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficientAndIncompatibleType_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark steel platebody", 50, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        ranger.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> ranger.equip(armor));
    }

    @Test
    void getValidWeaponTypes_WhenWeaponValid_ShouldEquipWeapon() {
        // Arrange
        Weapon weapon = new Weapon("Bow", 0, WeaponType.BOW, 8d, 1.5);

        // Act
        ranger.equip(weapon);
        final Item equippedWeapon = ranger.getEquipment(Slot.WEAPON);

        // Assert
        assertEquals(weapon, equippedWeapon);
    }

    @Test
    void getValidWeaponType_WhenWeaponTypeIncompatible_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> ranger.equip(weapon));
    }

    @Test
    void getValidWeaponType_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Dark steel axe", 50, WeaponType.AXE, 100d, 1.4d);

        // Act
        ranger.setLevel(49);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> ranger.equip(weapon));
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndNoEquipment_EqualsBaseAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = ranger.getBasePrimaryAttributes();

        // Act
        ranger.setLevel(1);

        // Assert
        assertEquals(baseAttribs, ranger.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel2AndNoEquipment_EqualsSumOfBaseAndGainedAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = ranger.getBasePrimaryAttributes();

        // Act
        ranger.setLevel(2);
        PrimaryAttribute gainedAttribs = ranger.getGainedPrimaryAttributes();

        // Assert
        assertEquals(baseAttribs.add(gainedAttribs), ranger.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndArmor_EqualsSumOfBaseAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, armorAttribs);
        PrimaryAttribute baseAttribs = ranger.getBasePrimaryAttributes();
        ranger.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = ranger.getEquippedArmorAttributes();

        // Assert
        assertEquals(baseAttribs.add(equippedArmorAttributes), ranger.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel10AndArmor_EqualsSumOfBaseAndGainedAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, armorAttribs);
        PrimaryAttribute baseAttribs = ranger.getBasePrimaryAttributes();
        PrimaryAttribute gainedAttribs = ranger.getGainedPrimaryAttributes();
        ranger.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = ranger.getEquippedArmorAttributes();

        // Assert
        assertEquals(
                baseAttribs
                        .add(equippedArmorAttributes)
                        .add(gainedAttribs),
                ranger.getTotalPrimaryAttributes()
        );
    }

    @Test
    void getGainedPrimaryAttributes_WhenLevel1_ReturnsZeroAttributes() {
        // Act
        ranger.setLevel(1);

        // Assert
        assertEquals(PrimaryAttribute.of(0, 0, 0), ranger.getGainedPrimaryAttributes());
    }

    @Test
    void getDPS_WhenLevel1AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(1);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(1 * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDPS_WhenLevel10AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(10);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(1 + (0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeapon_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(1);
        Weapon weapon = new Weapon("Bow", 0, WeaponType.BOW, 8d, 1.5);
        ranger.equip(weapon);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel10AndWeapon_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(10);
        Weapon weapon = new Weapon("Bow", 0, WeaponType.BOW, 8d, 1.5);
        ranger.equip(weapon);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, armorAttribs);
        Weapon weapon = new Weapon("Bow", 0, WeaponType.BOW, 8d, 1.5);
        ranger.equip(weapon);
        ranger.equip(armor);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel10AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        ranger.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Rangers' tunic", 0, Slot.BODY, ArmorType.LEATHER, armorAttribs);
        Weapon weapon = new Weapon("Bow", 0, WeaponType.BOW, 8d, 1.5);
        ranger.equip(weapon);
        ranger.equip(armor);
        PrimaryAttribute attribs = ranger.getTotalPrimaryAttributes();

        // Act
        Double dps = ranger.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

}
