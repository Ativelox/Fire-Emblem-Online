package de.ativelox.feo.client.model.gfx.animation;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ComposedAnimation implements IUpdateable, IRenderable {

    private IAnimation mCurrent;
    private Deque<IAnimation> mAnimations;

    public ComposedAnimation(Deque<IAnimation> animations) {
        mAnimations = animations;
        mCurrent = animations.removeFirst();
        mCurrent.start();

    }

    public ComposedAnimation(IAnimation... animations) {
        this(new ArrayDeque<>(Arrays.asList(animations)));

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mCurrent.render(g);
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mCurrent.isFinished()) {
            mCurrent = mAnimations.removeFirst();
            mCurrent.start();
        }
        mCurrent.update(ts);

    }

    public void addLast(final IAnimation e) {
        mAnimations.addLast(e);

    }

    public void addFirst(final IAnimation e) {
        mAnimations.addFirst(e);

    }

}
