package de.ativelox.feo.client.model.sound;

import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DummySoundPlayer implements ISoundPlayer {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void update(TimeSnapshot ts) {
        // TODO Auto-generated method stub

    }

    @Override
    public void play(EMusic music) {
        // TODO Auto-generated method stub

    }

    @Override
    public void play(ESoundEffect effect) {
        // TODO Auto-generated method stub

    }

}
