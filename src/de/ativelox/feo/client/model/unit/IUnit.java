package de.ativelox.feo.client.model.unit;

import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IUnit extends IRenderable, IUpdateable, ISpatial, ISelectable, ICanMove {

    public int getMovement();

    public int getRange();

    public void setRange(int range);

    public void moveInstantly(int x, int y);

}
