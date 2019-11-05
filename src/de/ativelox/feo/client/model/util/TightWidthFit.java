package de.ativelox.feo.client.model.util;

import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TightWidthFit implements Function<BufferedImage, BufferedImage> {

    private final int mRgbIgnore;

    public TightWidthFit(int rgbIgnore) {
        mRgbIgnore = rgbIgnore;
    }

    @Override
    public BufferedImage apply(BufferedImage t) {
        int leftBound = t.getWidth();
        int rightBound = 0;

        for (int i = 0; i < t.getWidth(); i++) {
            for (int j = 0; j < t.getHeight(); j++) {
                if (t.getRGB(i, j) == mRgbIgnore) {
                    continue;
                }
                if (i < leftBound) {
                    leftBound = i;
                }
                if (i > rightBound) {
                    rightBound = i;
                }
            }
        }
        if (leftBound > rightBound) {
            return t;
        }

        return t.getSubimage(leftBound, 0, rightBound - leftBound + 1, t.getHeight());
    }

}
