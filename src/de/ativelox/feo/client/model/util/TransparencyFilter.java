package de.ativelox.feo.client.model.util;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TransparencyFilter extends RGBImageFilter {

    private final Color mColor;

    public TransparencyFilter(final Color color) {
        mColor = color;
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        if (rgb == mColor.getRGB()) {
            return 0x00FFFFF & rgb;
        } else {
            return rgb;
        }
    }

}
