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
    void getName_ReturnsOriginalValue() {
        // Assert
        assertEquals(ORIGINAL_NAME, mage.getName());
    }

    @Test
    void toString_ContainsAttributes() {
        // Act
        String string = mage.toString();

        // Assert
        assertThat(string, containsString(mage.getTotalPrimaryAttributes().toString()));
    }

    @Test
    void toString_ContainsEquipment() {
        // Arrange
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 2, 8));
        mage.equip(armor);

        // Act
        String string = mage.toString();

        // Assert
        assertThat(string, containsString(armor.toString()));
    }

    @Test
    void getBasePrimaryAttributes_ReturnsNonNullValues() {
        // Arrange
        final PrimaryAttribute attribs = mage.getBasePrimaryAttributes();

        // Assert
        assertThat(
                List.of(attribs.strength(), attribs.dexterity(), attribs.intelligence()),
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
                List.of(before.strength(), before.dexterity(), before.intelligence()),
                not(contains(after.strength(), after.dexterity(), after.intelligence()))
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

        // Act
        mage.setLevel(10);

        // Assert
        assertThrows(InvalidArmorException.class, () -> mage.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark wizard robe", 50, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 20, 100));

        // Act
        mage.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> mage.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficientAndIncompatibleType_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark steel platebody", 50, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        mage.setLevel(49);

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

        // Act
        mage.setLevel(49);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> mage.equip(weapon));
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndNoEquipment_EqualsBaseAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = mage.getBasePrimaryAttributes();

        // Act
        mage.setLevel(1);

        // Assert
        assertEquals(baseAttribs, mage.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel2AndNoEquipment_EqualsSumOfBaseAndGainedAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = mage.getBasePrimaryAttributes();

        // Act
        mage.setLevel(2);
        PrimaryAttribute gainedAttribs = mage.getGainedPrimaryAttributes();

        // Assert
        assertEquals(baseAttribs.add(gainedAttribs), mage.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndArmor_EqualsSumOfBaseAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, armorAttribs);
        PrimaryAttribute baseAttribs = mage.getBasePrimaryAttributes();
        mage.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = mage.getEquippedArmorAttributes();

        // Assert
        assertEquals(baseAttribs.add(equippedArmorAttributes), mage.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel10AndArmor_EqualsSumOfBaseAndGainedAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, armorAttribs);
        PrimaryAttribute baseAttribs = mage.getBasePrimaryAttributes();
        PrimaryAttribute gainedAttribs = mage.getGainedPrimaryAttributes();
        mage.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = mage.getEquippedArmorAttributes();

        // Assert
        assertEquals(
                baseAttribs
                        .add(equippedArmorAttributes)
                        .add(gainedAttribs),
                mage.getTotalPrimaryAttributes()
        );
    }

    @Test
    void getGainedPrimaryAttributes_WhenLevel1_ReturnsZeroAttributes() {
        // Act
        mage.setLevel(1);

        // Assert
        assertEquals(PrimaryAttribute.of(0, 0, 0), mage.getGainedPrimaryAttributes());
    }

    @Test
    void getDPS_WhenLevel1AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(1);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(1 * (1 + 0.01d * attribs.intelligence()), dps);
    }

    @Test
    void getDPS_WhenLevel10AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(10);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(1 + (0.01d * attribs.intelligence()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeapon_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(1);
        Weapon weapon = new Weapon("Wand", 0, WeaponType.WAND, 10d, 1.5);
        mage.equip(weapon);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.intelligence()), dps);
    }

    @Test
    void getDps_WhenLevel10AndWeapon_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(10);
        Weapon weapon = new Weapon("Wand", 0, WeaponType.WAND, 10d, 1.5);
        mage.equip(weapon);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.intelligence()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, armorAttribs);
        Weapon weapon = new Weapon("Wand", 0, WeaponType.WAND, 10d, 1.5);
        mage.equip(weapon);
        mage.equip(armor);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.intelligence()), dps);
    }

    @Test
    void getDps_WhenLevel10AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        mage.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Wizard robe", 0, Slot.BODY, ArmorType.CLOTH, armorAttribs);
        Weapon weapon = new Weapon("Wand", 0, WeaponType.WAND, 10d, 1.5);
        mage.equip(weapon);
        mage.equip(armor);
        PrimaryAttribute attribs = mage.getTotalPrimaryAttributes();

        // Act
        Double dps = mage.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.intelligence()), dps);
    }

}
