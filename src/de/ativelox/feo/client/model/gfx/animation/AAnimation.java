package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AAnimation extends SpatialObject implements IAnimation {

    protected final Map<Integer, List<Function<TimeSnapshot, Boolean>>> mHookMapping;

    protected final boolean mIsLooping;

    protected final EAnimationDirection mAnimationDirection;

    protected long mPlayTime;

    protected final BufferedImage[] mSequence;

    protected boolean mIsStopped;

    protected int mNext;

    protected boolean mHidden;

    public AAnimation(BufferedImage[] sequence, EAnimationDirection direction, boolean looping, long playTime,
            int width, int height) {
        super(0, 0, width, height);

        mHookMapping = new HashMap<>();

        mAnimationDirection = direction;
        mIsLooping = looping;
        mPlayTime = playTime;
        mSequence = sequence;

        mNext = 0;
        mIsStopped = true;
    }

    @Override
    public boolean isLooping() {
        return mIsLooping;

    }

    @Override
    public abstract boolean isFinished();

    public abstract void update(TimeSnapshot ts);

    protected boolean hookRoutine(TimeSnapshot ts) {
        if (mHookMapping.containsKey(mNext + 1)) {
            boolean isContinuing = true;

            for (final Function<TimeSnapshot, Boolean> hook : mHookMapping.get(mNext + 1)) {
                boolean hookResult = hook.apply(ts);
                isContinuing = isContinuing && hookResult;
            }
            return isContinuing;
        }
        return true;
    }

    @Override
    public abstract void render(DepthBufferedGraphics g);

    @Override
    public void start() {
        mIsStopped = false;

    }

    @Override
    public void stop() {
        mNext = mSequence.length - 1;
        mIsStopped = true;

    }

    @Override
    public void reset() {
        mNext = 0;

    }

    @Override
    public void hide() {
        mHidden = true;
    }

    @Override
    public void show() {
        mHidden = false;
    }

    @Override
    public Iterator<BufferedImage> iterator() {
        return new AnimationIterator(mSequence);
    }

    @Override
    public void addHook(int frame, Function<TimeSnapshot, Boolean> hook) {
        if (!mHookMapping.containsKey(frame)) {
            mHookMapping.put(frame, new ArrayList<>());
        }
        mHookMapping.get(frame).add(hook);

    }

    @Override
    public void addEndHook(Function<TimeSnapshot, Boolean> hook) {
        addHook(mSequence.length, hook);
    }

    @Override
    public boolean isHidden() {
        return mHidden;
    }
}
