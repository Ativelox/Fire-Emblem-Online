package de.ativelox.feo.client.model.gfx.animation;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class OffsetLoopingAnimation extends DefaultLoopingAnimation {

    private final int mOffsetX;
    private final int mOffsetY;

    public OffsetLoopingAnimation(Image[] sequence, EAnimationDirection animationDirection, long playTime, int width,
            int height, int offsetX, int offsetY) {
        super(sequence, animationDirection, playTime, width, height);

        mOffsetX = offsetX;
        mOffsetY = offsetY;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mHidden) {
            return;
        }
        g.drawImage(mSequence[mNext], getX() + mOffsetX, getY() + mOffsetY, getWidth(), getHeight());

    }
}
