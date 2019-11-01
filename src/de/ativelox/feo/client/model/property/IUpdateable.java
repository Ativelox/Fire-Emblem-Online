package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * Provides an interface for everything that should be updateable.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IUpdateable {

    /**
     * Updates the state of this class.
     * 
     * @param ts The {@link TimeSnapshot} which can be used to determine the time
     *           that has passed since the last frame was completely rendered.
     */
    void update(TimeSnapshot ts);

}
