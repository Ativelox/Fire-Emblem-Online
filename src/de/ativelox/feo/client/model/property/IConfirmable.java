package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.property.callback.IConfirmListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IConfirmable {

    void confirm();

    void add(IConfirmListener listener);

    void remove(IConfirmListener listener);

}
