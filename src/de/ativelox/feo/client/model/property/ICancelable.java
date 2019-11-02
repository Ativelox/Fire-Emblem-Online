package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.property.callback.ICancelListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ICancelable {

    void cancel();

    void addCancelListener(ICancelListener listener);

    void removeCancelListener(ICancelListener lsitener);

}
