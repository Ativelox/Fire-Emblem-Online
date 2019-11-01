package de.ativelox.feo.client.model.gfx.animation;

import java.awt.Graphics2D;
import java.awt.Image;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * Provides non-looping animations. The animation will start playing by a call
 * to {@link IAnimation#start()} and will stop when it has finished. Note that
 * while the animation is not started a call to
 * {@link IAnimation#render(Graphics2D)} will render the first frame of the
 * animation and when the animation is finished the last frame of the animation
 * will be rendered respectively.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultNonLoopingAnimation extends AAnimation {

    private long mTimePassed;

    private long mFrameCounter;

    private long mFrameSpacing;

    private int mDirection;

    private boolean mIsFinished;

    public DefaultNonLoopingAnimation(Image[] sequence, EAnimationDirection direction, long playTime, int width,
            int height) {
        super(sequence, direction, false, playTime, width, height);

        if (direction == EAnimationDirection.FORWARD_BACKWARD || direction == EAnimationDirection.BACKWARD_FORWARD) {
            Logger.get().log(ELogType.ERROR, direction + " isn't supported for non-looping animations.");
            throw new IllegalArgumentException();

        }

        mTimePassed = 0;
        mFrameCounter = 1;

        mFrameSpacing = playTime / sequence.length;

        mIsFinished = false;

        switch (direction) {
        case BACKWARD:
            mNext = sequence.length - 1;
            mDirection = -1;
            break;
        case FORWARD:
            mNext = 0;
            mDirection = 1;
            break;
        default:
            break;

        }
    }

    @Override
    public IAnimation copy() {
        Image[] sequence = new Image[mSequence.length];

        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = mSequence[i];

        }
        return new DefaultNonLoopingAnimation(sequence, mAnimationDirection, mPlayTime, mWidth, mHeight);
    }

    @Override
    public boolean isFinished() {
        return mIsFinished;

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsStopped || isFinished()) {
            return;
        }

        if (mTimePassed >= mFrameSpacing * mFrameCounter) {
            if (mNext >= mSequence.length - 1 && mDirection > 0) {
                // at the back of the animation
                this.mIsFinished = true;
                return;

            } else if (mNext <= 0 && mDirection < 0) {
                // at the front of the animation
                this.mIsFinished = true;
                return;
            }
            mNext += mDirection;
            mFrameCounter++;

        }
        mTimePassed += ts.getPassed();

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mHidden) {
            return;
        }

        g.drawImage(mSequence[mNext], getX(), getY(), getWidth(), getHeight());

    }
}
