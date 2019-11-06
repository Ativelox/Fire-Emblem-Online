package de.ativelox.feo.client.model.gfx.animation.hook;

import java.util.function.Function;

import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class NonBlockingSoundHook implements Function<TimeSnapshot, Boolean> {

    private final ESoundEffect mEffect;

    private boolean mIsFinished;

    public NonBlockingSoundHook(ESoundEffect effect) {
        mEffect = effect;
        mIsFinished = false;
    }

    @Override
    public Boolean apply(TimeSnapshot t) {
        if (mIsFinished) {
            return true;
        }
        SoundPlayer.get().play(mEffect);
        mIsFinished = true;

        return true;
    }

}
