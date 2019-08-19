package model.items.normal;

import model.items.normal.AbstractItem;

/**
 * This class represents a sword type item.
 * <p>
 * Swords are strong against axes and weak against spears.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Sword extends AbstractItem {

  /**
   * Creates a new Sword.
   *
   * @param name
   *     the name that identifies the weapon
   * @param power
   *     the base damage pf the weapon
   * @param minRange
   *     the minimum range of the weapon
   * @param maxRange
   *     the maximum range of the weapon
   */
  public Sword(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
    this.strong = "Axe";
    this.weak = "Spear";
    this.attack = true;
  }
  @Override
  public boolean Strong(String item){

    if(this.getName() == item){
      return true;
    }
    return false;
  }

  @Override
  public boolean Weak(String item){

    if(this.getName() == item){
      return true;
    }
    return false;
  }
}