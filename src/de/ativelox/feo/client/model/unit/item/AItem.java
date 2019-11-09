package de.ativelox.feo.client.model.unit.item;

import java.awt.Image;
import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AItem implements IItem {

    protected final String mName;
    protected final Image mImage;

    protected BiConsumer<IUnit, IItem> mUsageContract;
    protected IUnit mUnit;

    public AItem(final String name, final Image image, final IUnit unit, final BiConsumer<IUnit, IItem> use) {
        mName = name;
        mImage = image;
        mUnit = unit;
        mUsageContract = use;

    }

    public AItem(final String name, final Image image, final BiConsumer<IUnit, IItem> use) {
        this(name, image, null, use);

    }

    public AItem(final String name, final Image image) {
        this(name, image, null, (p, u) -> {
        });

    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public Image getImage() {
        return mImage;
    }

    @Override
    public void use() {
        mUsageContract.accept(mUnit, this);

    }

    @Override
    public void setOwner(IUnit unit) {
        mUnit = unit;

    }

    @Override
    public IUnit getOwner() {
        return mUnit;
    }

    @Override
    public abstract IItem copy();

    @Override
    public void overwriteUsage(BiConsumer<IUnit, IItem> usage) {
        mUsageContract = usage;

    }

}
