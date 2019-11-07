package de.ativelox.feo.client.model.gfx.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ComposedAnimation implements IUpdateable, IRenderable, Iterable<IAnimation> {

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
        this(new ArrayList<>(Arrays.asList(animations)));

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
            if (mCurrent + 1 % 2 == 0) {
                mAnimations.get(mCurrent - 1).hide();
                mAnimations.get(mCurrent).hide();
                mAnimations.get(mCurrent + 1).show();
                mAnimations.get(mCurrent + 2).show();
            }
            mCurrent++;
            mAnimations.get(mCurrent).start();
            mAnimations.get(mCurrent).show();
        }
        mAnimations.forEach(c -> c.update(ts));

    }

    public boolean isFinished() {
        return mIsFinished;
    }

    public void addLast(final IAnimation e) {
        e.hide();
        mAnimations.add(e);

    }

    public void addFirst(final IAnimation e) {
        e.hide();
        mAnimations.add(0, e);

    }

    public void addLast(final ComposedAnimation e) {
        for (final IAnimation animation : e) {
            this.addLast(animation);
        }
    }

    public void addFirst(final ComposedAnimation e) {
        for (final IAnimation animation : e) {
            this.addFirst(animation);
        }
    }

    @Override
    public Iterator<IAnimation> iterator() {
        return mAnimations.iterator();
    }

}
