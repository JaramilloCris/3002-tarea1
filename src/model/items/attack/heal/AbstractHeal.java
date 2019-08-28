package model.items.attack.heal;


import model.items.IEquipableItem;
import model.units.IUnit;

public abstract class AbstractHeal implements IHeal {



    private final String name;
    private final double power;
    protected int maxRange;
    protected int minRange;
    private IUnit owner;
    protected boolean magic;
    protected boolean attack;


    /**
     * Constructor for a default item without any special behaviour.
     *
     * @param name
     *     the name of the item
     * @param power
     *     the power of the item (this could be the amount of damage or healing the item does)
     * @param minRange
     *     the minimum range of the item
     * @param maxRange
     *     the maximum range of the item
     */
    public AbstractHeal(final String name, final int power, final int minRange, final int maxRange) {
        this.name = name;
        this.power = power;
        this.minRange = Math.max(minRange, 1);
        this.maxRange = Math.max(maxRange, this.minRange);
        this.magic = false;
        this.attack = false;
    }


    public void equipTo(final IUnit unit) {
        unit.setEquippedItem(this);
        owner = unit;
    }

    public IUnit getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public double getPower() {
        return power;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    @Override
    public double counterSword(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterAxe(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterSpear(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterBow(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterAnima(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterDark(IEquipableItem item){

        return 0;
    }
    @Override
    public double counterLight(IEquipableItem item){

        return 0;
    }


    public double heal(IHeal item){

        return item.getPower();
    }

}
