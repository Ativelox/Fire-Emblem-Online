package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.IConfirmable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IConfirmListener {
    
    void onConfirm(IConfirmable confirmable);

}
