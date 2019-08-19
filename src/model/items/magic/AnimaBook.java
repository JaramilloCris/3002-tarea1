package model.items.magic;

import model.items.magic.AbstractBook;

/**
 *
 * This class represents a AnimaBook of the magic type
 * This item
 *
 *
 */

public class AnimaBook extends AbstractBook {

    /**
     * Creates a new AnimaBook
     *
     * @param name
     *      the name that identifies the weapon
     * @param power
     *      the base damage pf the weapon
     * @param minRange
     *      the minimum range of the weapon
     * @param maxRange
     *       the maximum range of the weapon
     */

    public AnimaBook(final String name, final int power, final int minRange, final int maxRange){

        super(name, power, minRange, maxRange);
        this.strong = "LightBook";

        this.attack = true;
    }


}