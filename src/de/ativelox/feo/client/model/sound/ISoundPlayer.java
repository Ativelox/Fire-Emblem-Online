package de.ativelox.feo.client.model.sound;

import de.ativelox.feo.client.model.property.IPriorityUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ISoundPlayer extends IPriorityUpdateable {

    void play(EMusic music);

    void play(ESoundEffect effect);

}
