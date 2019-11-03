package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class AnimationIterator implements Iterator<BufferedImage> {

    private final BufferedImage[] mSequence;
    private int mIndex;

    public AnimationIterator(BufferedImage[] sequence) {
        mSequence = sequence;
        mIndex = 0;

    }

    @Override
    public boolean hasNext() {
        return mIndex < mSequence.length;
    }

    @Override
    public BufferedImage next() {
        BufferedImage result = mSequence[mIndex];
        mIndex++;
        return result;

    }
}
