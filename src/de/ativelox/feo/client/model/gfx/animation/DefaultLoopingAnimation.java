package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultLoopingAnimation extends ALoopingAnimation {

    private long mFrameSpacing;

    private long mTimePassed;

    private int mDirection;

    private long mFrameCounter;

    private boolean mIsSmooth;

    public DefaultLoopingAnimation(BufferedImage[] sequence, EAnimationDirection animationDirection, long playTime,
            int width, int height) {
        super(sequence, animationDirection, playTime, width, height);

        mTimePassed = 0;
        mFrameCounter = 1;

        mFrameSpacing = mPlayTime / sequence.length;

        mIsSmooth = false;

        switch (animationDirection) {
        case BACKWARD:
            mNext = sequence.length - 1;
            mDirection = -1;
            break;
        case BACKWARD_FORWARD:
            mNext = sequence.length - 1;
            mIsSmooth = true;
            mDirection = -1;
            break;
        case FORWARD:
            mNext = 0;
            mDirection = 1;
            break;
        case FORWARD_BACKWARD:
            mNext = 0;
            mIsSmooth = true;
            mDirection = 1;
            break;
        default:
            break;

        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsStopped) {
            return;
        }
        hookRoutine(ts);

        if (mTimePassed >= mFrameSpacing * mFrameCounter) {
            if (mNext >= mSequence.length - 1 && mDirection > 0) {
                // at the back of the animation
                if (!mIsSmooth) {
                    mNext = -1;

                } else {
                    mDirection = -1;
                }

            } else if (mNext <= 0 && mDirection < 0) {
                // at the front of the animation
                if (!mIsSmooth) {
                    mNext = mSequence.length;

                } else {
                    mDirection = 1;
                }
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

        g.drawImage(mSequence[mNext], getX(), getY(), getWidth(), getHeight(), 2);

    }

    @Override
    public void addHook(int frame, Function<TimeSnapshot, Boolean> hook) {
        // TODO Auto-generated method stub

    }
}
