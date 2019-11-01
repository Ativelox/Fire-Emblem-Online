package de.ativelox.feo.client.model.property.callback;

import java.util.Set;

import de.ativelox.feo.client.controller.input.EAction;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IActionListener {

    void onPress(Set<EAction> action);

    void onRelease(Set<EAction> action);

}
