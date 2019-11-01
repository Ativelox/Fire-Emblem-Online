package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.ISelectable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ISelectionListener {

    void onSelect(ISelectable selectable);

    void onDeSelect(ISelectable selectable);

}
