package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.property.callback.ISelectionListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ISelectable {

    void selected();

    void deSelected();

    boolean isSelected();

    default int getOrder() {
        return 0;
    }

    void add(ISelectionListener listener);

    void remove(ISelectionListener listener);

}
