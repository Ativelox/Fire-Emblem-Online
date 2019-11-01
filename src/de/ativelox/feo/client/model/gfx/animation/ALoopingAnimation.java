package de.ativelox.feo.client.model.gfx.animation;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class ALoopingAnimation extends AAnimation {

    public ALoopingAnimation(Image[] sequence, EAnimationDirection direction, long playTime, int width, int height) {
        super(sequence, direction, true, playTime, width, height);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public abstract void update(TimeSnapshot ts);

    @Override
    public abstract void render(DepthBufferedGraphics g);

}
