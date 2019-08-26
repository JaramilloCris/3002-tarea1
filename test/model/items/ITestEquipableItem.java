package model.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.items.IEquipableItem;
import model.units.IUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Defines some common methods for all the items tests
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface ITestEquipableItem{


    /**
     * Sets up the items to be tested
     */
    void setUp();

    /**
     * Sets up a correctly implemented item that's going to be tested
     */
    void setTestItem();

    /**
     * Sets up an item with wrong ranges setted.
     */
    void setWrongRangeItem();

    /**
     * Sets the unit that will be equipped with the test item
     */
    void setTestUnit();

    /**
     * Checks that the tested item cannot have ranges outside of certain bounds.
     */
    @Test
    void incorrectRangeTest();

    IEquipableItem getWrongTestItem();

    /**
     * Tests that the constructor for the tested item works properly
     */
    @Test
    void constructorTest();

    /**
     * @return the expected name of the item
     */
    String getExpectedName();

    /**
     * @return the item being tested
     */
    IEquipableItem getTestItem();

    /**
     * @return the expected power of the Item
     */
    int getExpectedBasePower();

    /**
     * @return the expected minimum range of the item
     */
    int getExpectedMinRange();

    /**
     * @return the expected maximum range of the item
     */
    int getExpectedMaxRange();

    /**
     * Checks that the Item can be correctly equipped to a unit
     */
    @Test
    void equippedToTest();

    /**
     * @return a unit that can equip the item being tested
     */
    IUnit getTestUnit();

    void damageTest();
}