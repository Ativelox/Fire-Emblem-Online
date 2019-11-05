package de.ativelox.feo.client.model.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Inventory {

    private final IItem[] mInventory;

    private int mLimit;

    private int mSize;

    public Inventory(int max, IItem... items) {
        mInventory = new IItem[max];

        mLimit = max;

        for (int i = 0; i < Math.min(items.length, max); i++) {
            mInventory[i] = items[i];
            mSize++;

        }
    }

    public Inventory(IItem... items) {
        this(5, items);

    }

    public int getLimit() {
        return mLimit;

    }

    public IItem[] getItems() {
        return mInventory;

    }

    public Collection<IWeapon> getSorted(Comparator<? super IWeapon> comparator) {
        List<IWeapon> weapons = new ArrayList<>();

        for (final IWeapon weapon : this.getWeapons()) {
            weapons.add(weapon);
        }
        weapons.sort(comparator);
        return weapons;

    }

    public Collection<IWeapon> getWeapons(Predicate<IWeapon> predicate) {
        Collection<IWeapon> weapons = new ArrayList<>();

        for (final IWeapon weapon : this.getWeapons()) {
            if (predicate.test(weapon)) {
                weapons.add(weapon);
            }
        }
        return weapons;
    }

    public IWeapon[] getWeapons() {
        List<IWeapon> temp = new ArrayList<>();

        for (final IItem item : mInventory) {
            if (item instanceof IWeapon) {
                temp.add((IWeapon) item);
            }
        }

        IWeapon[] result = new IWeapon[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);

        }
        return result;

    }

    public void add(IItem item) {
        mInventory[mSize] = item;
        mSize++;
    }

    public void remove() {

    }

    public IItem swap(IItem itemAdd, int index) {
        IItem result = mInventory[index];
        mInventory[index] = itemAdd;

        return result;
    }

    public IItem swap(IItem itemAdd, IItem itemRemove) {
        IItem result = null;

        for (int i = 0; i < mInventory.length; i++) {
            if (mInventory[i] == itemRemove) {
                result = mInventory[i];
                mInventory[i] = itemAdd;
                break;
            }
        }
        return result;

    }
}
