package de.ativelox.feo.client.model.unit.item.weapon;

import java.awt.Image;
import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.AItem;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.util.Range;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Weapon extends AItem implements IWeapon {

    private final int mDurability;

    private final Range<Integer> mRange;

    private final int mCrit;

    private final int mMight;

    private final int mAccuracy;

    private final int mWeight;

    private final EDamageType mDamageType;

    private int mCurrentDurability;

    public Weapon(String name, int durability, Range<Integer> range, int crit, int might, int accurracy, int weight,
            EDamageType damageType, Image image, IUnit owner, BiConsumer<IUnit, IItem> use) {
        super(name, image, owner, use);

        mCurrentDurability = durability;
        mDurability = durability;
        mRange = range;
        mCrit = crit;
        mMight = might;
        mAccuracy = accurracy;
        mWeight = weight;
        mDamageType = damageType;

    }

    public Weapon(String name, int durability, Range<Integer> range, int crit, int might, int accurracy, int weight,
            EDamageType damageType, Image image, BiConsumer<IUnit, IItem> use) {
        this(name, durability, range, crit, might, accurracy, weight, damageType, image, null, use);

    }

    public Weapon(String name, int durability, Range<Integer> range, int crit, int might, int accurracy, int weight,
            EDamageType damageType, Image image, IUnit owner) {
        this(name, durability, range, crit, might, accurracy, weight, damageType, image, owner, (p, u) -> {
        });
    }

    public Weapon(String name, int durability, Range<Integer> range, int crit, int might, int accurracy, int weight,
            EDamageType damageType, Image image) {
        this(name, durability, range, crit, might, accurracy, weight, damageType, image, (p, u) -> {
        });
    }

    @Override
    public int getCurrentDurability() {
        return mCurrentDurability;
    }

    @Override
    public int getMaximumDurability() {
        return mDurability;
    }

    @Override
    public Range<Integer> getRange() {
        return mRange;
    }

    @Override
    public int getCrit() {
        return mCrit;
    }

    @Override
    public int getMight() {
        return mMight;
    }

    @Override
    public int getAccuracy() {
        return mAccuracy;
    }

    @Override
    public int getWeight() {
        return mWeight;
    }

    @Override
    public EDamageType getDamageType() {
        return mDamageType;
    }

    @Override
    public IWeapon copy() {
        return new Weapon(mName, mDurability, mRange, mCrit, mMight, mAccuracy, mWeight, mDamageType, mImage,
                mUsageContract);

    }
}
