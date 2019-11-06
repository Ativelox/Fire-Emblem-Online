package de.ativelox.feo.client.model.sound;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SoundPlayer {

    private static ISoundPlayer SOUND_PLAYER = null;

    private SoundPlayer() {

    }

    public static ISoundPlayer get() {
        if (SOUND_PLAYER == null) {
            SOUND_PLAYER = new SimpleSoundPlayer();

        }
        return SOUND_PLAYER;

    }

}
