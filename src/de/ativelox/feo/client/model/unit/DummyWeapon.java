package de.ativelox.feo.client.model.unit;

import de.ativelox.feo.client.model.property.EDamageType;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DummyWeapon implements IWeapon {

    private final int mRange;
    private final String mName;

    public DummyWeapon(String name, int range) {
        mRange = range;
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getCurrentDurability() {
        return 40;
    }

    @Override
    public int getMaximumDurability() {
        return 45;
    }

    @Override
    public int getRange() {
        return mRange;
    }

    @Override
    public int getCrit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMight() {
        // TODO Auto-generated method stub
        return 20;
    }

    @Override
    public int getAccuracy() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public int getWeight() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public EDamageType getDamageType() {
        // TODO Auto-generated method stub
        return EDamageType.PHYSICAL;
    }

}
