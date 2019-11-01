package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.property.callback.IClickListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IClickable {

    void pressed(EClickType type, int relX, int relY);

    void released(EClickType type, int relX, int relY);

    void addClickListener(final IClickListener clickListener);
    
    void removeClickListener(final IClickListener clickListener);

}
