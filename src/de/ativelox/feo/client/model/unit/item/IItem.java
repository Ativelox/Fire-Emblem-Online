package de.ativelox.feo.client.model.unit.item;

import java.awt.Image;
import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IItem {

    String getName();

    Image getImage();

    void use();

    void overwriteUsage(BiConsumer<IUnit, IItem> usage);

    void setOwner(IUnit unit);

    IUnit getOwner();

    IItem copy();

}
