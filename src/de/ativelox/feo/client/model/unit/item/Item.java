package de.ativelox.feo.client.model.unit.item;

import java.awt.Image;
import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Item extends AItem {

    public Item(String name, Image image) {
        this(name, image, null, (p, u) -> {
        });

    }

    public Item(String name, Image image, IUnit unit, BiConsumer<IUnit, IItem> use) {
        super(name, image, unit, use);

    }

    @Override
    public IItem copy() {
        return new Item(mName, mImage, mUnit, mUsageContract);
    }

}
