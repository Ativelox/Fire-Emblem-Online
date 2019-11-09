package de.ativelox.feo.client.model.unit.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Inventory {

    private final List<IItem> mInventory;

    private int mLimit;

    private int mSize;

    private final IUnit mOwner;

    public Inventory(final IUnit owner, int max, IItem... items) {
        mInventory = new ArrayList<>();

        for (final IItem item : items) {
            this.add(item);
        }

        mOwner = owner;

        mLimit = max;

        for (int i = 0; i < Math.min(items.length, max); i++) {
            this.add(items[i]);

        }
    }

    public Inventory(final IUnit owner, IItem... items) {
        this(owner, 5, items);

    }

    public int getLimit() {
        return mLimit;

    }

    public List<IItem> getItems() {
        List<IItem> result = new ArrayList<>();

        for (final IItem item : mInventory) {
            if (item == null) {
                continue;
            }

            result.add(item);
        }
        return result;

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
        item.setOwner(mOwner);
        mInventory.add(item);
        mSize++;
    }

    public IItem swap(IItem itemAdd, int index) {
        IItem result = mInventory.remove(index);
        mInventory.add(index, itemAdd);

        return result;
    }

    public void remove(IItem item) {
        mInventory.remove(item);

    }

    public boolean contains(IWeapon weapon) {
        return mInventory.contains(weapon);
    }
}
