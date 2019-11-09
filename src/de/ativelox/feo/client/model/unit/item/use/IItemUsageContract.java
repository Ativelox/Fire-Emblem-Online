package de.ativelox.feo.client.model.unit.item.use;

import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.IItem;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IItemUsageContract extends BiConsumer<IUnit, IItem> {

}
