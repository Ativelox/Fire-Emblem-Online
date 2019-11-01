package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IRequireResource<T> {

    default T request() {
        return Assets.getFor(this);
    }

    EResource getResourceTypes();

}
