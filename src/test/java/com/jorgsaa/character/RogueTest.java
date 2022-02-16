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

class RogueTest {

    private static final String ORIGINAL_NAME = "Rogue";
    private Rogue rogue;

    @BeforeEach
    public void setUp() {
        // Arrange
        rogue = new Rogue(ORIGINAL_NAME);
    }

    @Test
    void getName_ReturnsOriginalValue() {
        // Assert
        assertEquals(ORIGINAL_NAME, rogue.getName());
    }

    @Test
    void getBasePrimaryAttributes_ReturnsNonNullValues() {
        // Arrange
        final PrimaryAttribute attribs = rogue.getBasePrimaryAttributes();

        // Assert
        assertThat(
                List.of(attribs.strength(), attribs.dexterity(), attribs.intelligence()),
                not(hasItem((Integer) null))
        );
    }

    @Test
    void getGainedPrimaryAttributes_AfterLevelUp_ReturnsChangedValues() {
        // Arrange
        final PrimaryAttribute before = rogue.getGainedPrimaryAttributes();

        // Act
        rogue.levelUp();
        final PrimaryAttribute after = rogue.getGainedPrimaryAttributes();

        // Assert
        assertThat(
                List.of(before.strength(), before.dexterity(), before.intelligence()),
                not(contains(after.strength(), after.dexterity(), after.intelligence()))
        );
    }

    @Test
    void getValidArmorTypes_WhenArmorValid_ShouldEquipArmor() {
        // Arrange
        Armor armor = new Armor("Iron chainmail", 0, Slot.BODY, ArmorType.MAIL, PrimaryAttribute.of(5, 1, 0));

        // Act
        rogue.equip(armor);
        final Item equippedArmor = rogue.getEquipment(armor.getSlot());

        // Assert
        assertEquals(armor, equippedArmor);
    }

    @Test
    void getValidArmorTypes_WhenArmorTypeIncompatible_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Steel platebody", 0, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        rogue.setLevel(10);

        // Assert
        assertThrows(InvalidArmorException.class, () -> rogue.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark wizard robe", 50, Slot.BODY, ArmorType.CLOTH, PrimaryAttribute.of(0, 20, 100));

        // Act
        rogue.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> rogue.equip(armor));
    }

    @Test
    void getValidArmorTypes_WhenCharacterLevelInsufficientAndIncompatibleType_ThrowsException() {
        // Arrange
        Armor armor = new Armor("Dark steel platebody", 50, Slot.BODY, ArmorType.PLATE, PrimaryAttribute.of(10, 0, 0));

        // Act
        rogue.setLevel(49);

        // Assert
        assertThrows(InvalidArmorException.class, () -> rogue.equip(armor));
    }

    @Test
    void getValidWeaponTypes_WhenWeaponValid_ShouldEquipWeapon() {
        // Arrange
        Weapon weapon = new Weapon("Iron dagger", 0, WeaponType.DAGGER, 20d, 0.4d);

        // Act
        rogue.equip(weapon);
        final Item equippedWeapon = rogue.getEquipment(Slot.WEAPON);

        // Assert
        assertEquals(weapon, equippedWeapon);
    }

    @Test
    void getValidWeaponType_WhenWeaponTypeIncompatible_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Steel axe", 0, WeaponType.AXE, 10d, 1.4d);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> rogue.equip(weapon));
    }

    @Test
    void getValidWeaponType_WhenCharacterLevelInsufficient_ThrowsException() {
        // Arrange
        Weapon weapon = new Weapon("Dark steel axe", 50, WeaponType.AXE, 100d, 1.4d);

        // Act
        rogue.setLevel(49);

        // Assert
        assertThrows(InvalidWeaponException.class, () -> rogue.equip(weapon));
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndNoEquipment_EqualsBaseAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = rogue.getBasePrimaryAttributes();

        // Act
        rogue.setLevel(1);

        // Assert
        assertEquals(baseAttribs, rogue.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel2AndNoEquipment_EqualsSumOfBaseAndGainedAttributes() {
        // Arrange
        PrimaryAttribute baseAttribs = rogue.getBasePrimaryAttributes();

        // Act
        rogue.setLevel(2);
        PrimaryAttribute gainedAttribs = rogue.getGainedPrimaryAttributes();

        // Assert
        assertEquals(baseAttribs.add(gainedAttribs), rogue.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel1AndArmor_EqualsSumOfBaseAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Iron chainmail", 0, Slot.BODY, ArmorType.MAIL, PrimaryAttribute.of(5, 1, 0));
        PrimaryAttribute baseAttribs = rogue.getBasePrimaryAttributes();
        rogue.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = rogue.getEquippedArmorAttributes();

        // Assert
        assertEquals(baseAttribs.add(equippedArmorAttributes), rogue.getTotalPrimaryAttributes());
    }

    @Test
    void getTotalPrimaryAttributes_WhenLevel10AndArmor_EqualsSumOfBaseAndGainedAndEquippedArmorAttributes() {
        // Arrange
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(0, 0, 10);
        Armor armor = new Armor("Iron chainmail", 0, Slot.BODY, ArmorType.MAIL, PrimaryAttribute.of(5, 1, 0));
        PrimaryAttribute baseAttribs = rogue.getBasePrimaryAttributes();
        PrimaryAttribute gainedAttribs = rogue.getGainedPrimaryAttributes();
        rogue.equip(armor);

        // Act
        PrimaryAttribute equippedArmorAttributes = rogue.getEquippedArmorAttributes();

        // Assert
        assertEquals(
                baseAttribs
                        .add(equippedArmorAttributes)
                        .add(gainedAttribs),
                rogue.getTotalPrimaryAttributes()
        );
    }

    @Test
    void getGainedPrimaryAttributes_WhenLevel1_ReturnsZeroAttributes() {
        // Act
        rogue.setLevel(1);

        // Assert
        assertEquals(PrimaryAttribute.of(0, 0, 0), rogue.getGainedPrimaryAttributes());
    }

    @Test
    void getDPS_WhenLevel1AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(1);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(1 * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDPS_WhenLevel10AndNoEquipment_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(10);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(1 + (0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeapon_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(1);
        Weapon weapon = new Weapon("Iron dagger", 0, WeaponType.DAGGER, 20d, 0.4d);
        rogue.equip(weapon);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel10AndWeapon_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(10);
        Weapon weapon = new Weapon("Iron dagger", 0, WeaponType.DAGGER, 20d, 0.4d);
        rogue.equip(weapon);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel1AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(5, 1, 0);
        Armor armor = new Armor("Iron chainmail", 0, Slot.BODY, ArmorType.MAIL, armorAttribs);
        Weapon weapon = new Weapon("Iron dagger", 0, WeaponType.DAGGER, 20d, 0.4d);
        rogue.equip(weapon);
        rogue.equip(armor);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

    @Test
    void getDps_WhenLevel10AndEquippedWeaponAndArmor_ReturnsExpectedValue() {
        // Arrange
        rogue.setLevel(1);
        PrimaryAttribute armorAttribs = PrimaryAttribute.of(5, 1, 0);
        Armor armor = new Armor("Iron chainmail", 0, Slot.BODY, ArmorType.MAIL, armorAttribs);
        Weapon weapon = new Weapon("Iron dagger", 0, WeaponType.DAGGER, 20d, 0.4d);
        rogue.equip(weapon);
        rogue.equip(armor);
        PrimaryAttribute attribs = rogue.getTotalPrimaryAttributes();

        // Act
        Double dps = rogue.getDPS();

        // Assert
        assertEquals(weapon.getDPS() * (1 + 0.01d * attribs.dexterity()), dps);
    }

}
