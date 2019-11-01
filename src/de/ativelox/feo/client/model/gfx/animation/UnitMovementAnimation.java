package de.ativelox.feo.client.model.gfx.animation;

import java.awt.Image;

import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitMovementAnimation extends OffsetLoopingAnimation {

    public UnitMovementAnimation(Image[] sequence, EAnimationDirection animationDirection, long playTime, int width,
            int height) {
        super(sequence, animationDirection, playTime, width, height, -8 * Display.INTERNAL_RES_FACTOR,
                -16 * Display.INTERNAL_RES_FACTOR);

    }
}
