package com.jorgsaa.attribute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimaryAttributeTest {

    private PrimaryAttribute attribute;
    public static final Integer STRENGTH_ORIGINAL = 1;
    public static final Integer DEXTERITY_ORIGINAL = 2;
    public static final Integer INTELLIGENCE_ORIGINAL = 3;

    @BeforeEach
    public void setUp() {
        // Arrange
        attribute = PrimaryAttribute.of(
                STRENGTH_ORIGINAL,
                DEXTERITY_ORIGINAL,
                INTELLIGENCE_ORIGINAL
        );
    }

    @Test
    void getStrength_WhenValid_ReturnsOriginal() {
        // Assert
        assertEquals(STRENGTH_ORIGINAL, attribute.strength());
    }

    @Test
    void getDexterity_WhenValid_ReturnsOriginal() {
        // Assert
        assertEquals(DEXTERITY_ORIGINAL, attribute.dexterity());
    }

    @Test
    void getIntelligence_WhenValid_ReturnsOriginal() {
        // Assert
        assertEquals(INTELLIGENCE_ORIGINAL, attribute.intelligence());
    }

    @Test
    void of_WhenValid_ReturnsCorrectAttributeValues() {

        // Act
        attribute = PrimaryAttribute.of(10, 20, 30);

        // Assert
        List<Integer> newPrimaryAttributes = List.of(
                attribute.strength(),
                attribute.dexterity(),
                attribute.intelligence()
        );

        assertThat(newPrimaryAttributes, not(anyOf(
                hasItem(STRENGTH_ORIGINAL),
                hasItem(DEXTERITY_ORIGINAL),
                hasItem(INTELLIGENCE_ORIGINAL)))
        );

        assertThat(newPrimaryAttributes, contains(10, 20, 30));
    }

    @Test
    void add_WhenValid_ReturnsCorrectAttributeValues() {

        // Act
        attribute = attribute.add(PrimaryAttribute.of(-1, -2, -3));

        // Assert
        List<Integer> newPrimaryAttributes = List.of(
                attribute.strength(),
                attribute.dexterity(),
                attribute.intelligence()
        );

        assertThat(
                newPrimaryAttributes,
                not(anyOf(
                        hasItem(STRENGTH_ORIGINAL),
                        hasItem(DEXTERITY_ORIGINAL),
                        hasItem(INTELLIGENCE_ORIGINAL)
                ))
        );

        assertThat(newPrimaryAttributes, contains(
                STRENGTH_ORIGINAL - 1,
                DEXTERITY_ORIGINAL - 2,
                INTELLIGENCE_ORIGINAL - 3)
        );
    }

    @Test
    void multiply_WhenValid_ReturnsCorrectAttributeValues() {

        // Arrange
        int multiplier = 100;

        // Act
        attribute = attribute.multiply(multiplier);

        // Assert
        List<Integer> newPrimaryAttributes = List.of(
                attribute.strength(),
                attribute.dexterity(),
                attribute.intelligence()
        );

        assertThat(
                newPrimaryAttributes,
                not(anyOf(
                        hasItem(STRENGTH_ORIGINAL),
                        hasItem(DEXTERITY_ORIGINAL),
                        hasItem(INTELLIGENCE_ORIGINAL)
                ))
        );

        assertThat(newPrimaryAttributes, contains(
                STRENGTH_ORIGINAL * multiplier,
                DEXTERITY_ORIGINAL * multiplier,
                INTELLIGENCE_ORIGINAL * multiplier)
        );
    }

}
