package de.ativelox.feo.client.model.sound;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IProduceSound {

    ESoundEffect getSoundEffect();

    default void play() {
        SoundPlayer.get().play(getSoundEffect());
    }

}
