package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.EClickType;
import de.ativelox.feo.client.model.property.IClickable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IClickListener {
    
    public void onClick(IClickable clickable, EClickType type);

}
