package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.ICancelable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ICancelListener {
    
    void onCancel(ICancelable cancelable);

}
