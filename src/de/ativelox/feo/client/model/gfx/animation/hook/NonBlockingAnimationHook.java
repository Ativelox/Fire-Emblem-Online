package de.ativelox.feo.client.model.gfx.animation.hook;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class NonBlockingAnimationHook implements IAnimationHook {

    /**
     * The animation that is to be hooked into another animation.
     */
    private final IAnimation mAnimation;

    /**
     * Creates a new {@link BlockingAnimationHook}.
     * 
     * @param animation The animation that is to be hooked into another animation.
     *                  Execution of this animation will start as soon as the
     *                  specified frame of the original frame is hit.
     */
    public NonBlockingAnimationHook(final IAnimation animation) {
        mAnimation = animation;

    }

    @Override
    public Boolean apply(TimeSnapshot t) {
        mAnimation.start();

        return true;
    }

    @Override
    public void update(TimeSnapshot ts) {
        mAnimation.update(ts);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mAnimation.render(g);

    }

}
