package de.ativelox.feo.client.model.gfx.animation;

import java.util.Arrays;
import java.util.List;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ComposedAnimation implements IUpdateable, IRenderable {

    private List<IAnimation> mAnimations;
    private int mCurrent;

    private boolean mIsFinished;

    public ComposedAnimation(List<IAnimation> animations) {
        mAnimations = animations;
        animations.get(0).start();
        mCurrent = 0;

        mIsFinished = false;

    }

    public ComposedAnimation(IAnimation... animations) {
        this(Arrays.asList(animations));

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mAnimations.forEach(c -> c.render(g));
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsFinished) {
            return;
        }

        if (mAnimations.get(mCurrent).isFinished()) {
            if (mCurrent >= mAnimations.size() - 1) {
                mIsFinished = true;
                return;
            }
            mCurrent++;
            mAnimations.get(mCurrent).start();
        }
        mAnimations.forEach(c -> c.update(ts));

    }

    public boolean isFinished() {
        return mIsFinished;
    }

    public void addLast(final IAnimation e) {
        mAnimations.add(mAnimations.size() - 1, e);

    }

    public void addFirst(final IAnimation e) {
        mAnimations.add(0, e);

    }

}
