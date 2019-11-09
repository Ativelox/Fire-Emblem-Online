package de.ativelox.feo.client.model.unit.item.weapon;

import java.awt.Image;

import de.ativelox.feo.client.model.property.EDamageType;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Weapon implements IWeapon {

    private final String mName;

    private final int mDurability;

    private final int mRange;

    private final int mCrit;

    private final int mMight;

    private final int mAccuracy;

    private final int mWeight;

    private final EDamageType mDamageType;
    
    private int mCurrentDurability;

    private final Image mImage;

    public Weapon(String name, int durability, int range, int crit, int might, int accurracy, int weight,
            EDamageType damageType, Image image) {

        mCurrentDurability = durability;
        mName = name;
        mDurability = durability;
        mRange = range;
        mCrit = crit;
        mMight = might;
        mAccuracy = accurracy;
        mWeight = weight;
        mDamageType = damageType;
        mImage = image;

    }

    @Override
    public String getName() {
        return mName;
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
    public int getRange() {
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
    public Image getImage() {
        return mImage;
    }

}
