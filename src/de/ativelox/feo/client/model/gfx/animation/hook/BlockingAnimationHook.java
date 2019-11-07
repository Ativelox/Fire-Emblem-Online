package de.ativelox.feo.client.model.gfx.animation.hook;

import java.util.function.Function;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * An animation hook provides the possibility to insert an animation into an
 * existing one at any frame in the original animation. The inserted animation
 * will start when the specified frame of the original animation is hit, as
 * specified by {@link IAnimation#addHook(int, Function)}, and the original
 * animation will continue execution as soon as the inserted animation has
 * finished execution. This can be chained, by hooking another animation to an
 * already existing hook, etc.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BlockingAnimationHook implements IAnimationHook {

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
    public BlockingAnimationHook(final IAnimation animation) {
        mAnimation = animation;

    }

    @Override
    public Boolean apply(TimeSnapshot t) {
        mAnimation.start();

        return mAnimation.isFinished();
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
