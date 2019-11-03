package de.ativelox.feo.client.model.unit;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Palette {

    public static Map<Integer, Integer> BLUE_GRAY;
    public static Map<Integer, Integer> BLUE_RED;
    public static Map<Integer, Integer> BLUE_GREEN;
    public static Map<Integer, Integer> BLUE_VIOLET;
    public static Map<Integer, Integer> GRAY_BLUE;

    public static Map<Integer, Integer> RED_GRAY;
    public static Map<Integer, Integer> GRAY_RED;

    public static Map<Integer, Integer> GREEN_GRAY;
    public static Map<Integer, Integer> GRAY_GREEN;

    public static Map<Integer, Integer> VIOLET_GRAY;
    public static Map<Integer, Integer> GRAY_VIOLET;

    public static void init() {
        BLUE_RED = new HashMap<>();
        BLUE_GRAY = new HashMap<>();
        BLUE_GREEN = new HashMap<>();
        BLUE_VIOLET = new HashMap<>();
        GRAY_BLUE = new HashMap<>();
        RED_GRAY = new HashMap<>();
        GRAY_RED = new HashMap<>();
        GREEN_GRAY = new HashMap<>();
        GRAY_GREEN = new HashMap<>();
        VIOLET_GRAY = new HashMap<>();
        GRAY_VIOLET = new HashMap<>();

        BufferedImage blue = Assets.getFor(EResource.PALETTE, "blue.png");
        BufferedImage red = Assets.getFor(EResource.PALETTE, "red.png");
        BufferedImage green = Assets.getFor(EResource.PALETTE, "green.png");
        BufferedImage gray = Assets.getFor(EResource.PALETTE, "gray.png");
        BufferedImage violet = Assets.getFor(EResource.PALETTE, "violet.png");

        int[] bluePixels = blue.getRGB(0, 0, blue.getWidth(), blue.getHeight(), null, 0, blue.getWidth());
        int[] redPixels = red.getRGB(0, 0, red.getWidth(), red.getHeight(), null, 0, red.getWidth());
        int[] greenPixels = green.getRGB(0, 0, green.getWidth(), green.getHeight(), null, 0, green.getWidth());
        int[] grayPixels = gray.getRGB(0, 0, gray.getWidth(), gray.getHeight(), null, 0, gray.getWidth());
        int[] violetPixels = violet.getRGB(0, 0, violet.getWidth(), violet.getHeight(), null, 0, violet.getWidth());

        for (int i = 0; i < bluePixels.length; i++) {
            if (bluePixels[i] != redPixels[i]) {
                BLUE_RED.put(bluePixels[i], redPixels[i]);
            }
            if (bluePixels[i] != greenPixels[i]) {
                BLUE_GREEN.put(bluePixels[i], greenPixels[i]);
            }
            if (bluePixels[i] != grayPixels[i]) {
                BLUE_GRAY.put(bluePixels[i], grayPixels[i]);
                GRAY_BLUE.put(grayPixels[i], bluePixels[i]);
            }
            if (bluePixels[i] != violetPixels[i]) {
                BLUE_VIOLET.put(bluePixels[i], violetPixels[i]);
            }
            if (redPixels[i] != grayPixels[i]) {
                RED_GRAY.put(redPixels[i], grayPixels[i]);
                GRAY_RED.put(grayPixels[i], redPixels[i]);

            }
            if (greenPixels[i] != grayPixels[i]) {
                GREEN_GRAY.put(greenPixels[i], grayPixels[i]);
                GRAY_GREEN.put(grayPixels[i], greenPixels[i]);

            }
            if (violetPixels[i] != grayPixels[i]) {
                VIOLET_GRAY.put(violetPixels[i], grayPixels[i]);
                GRAY_VIOLET.put(grayPixels[i], violetPixels[i]);

            }
        }
    }

    public static void convertTo(IAnimation toConvert, Map<Integer, Integer> pixelMapping) {
        for (BufferedImage image : toConvert) {
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            for (int i = 0; i < pixels.length; i++) {
                if (!pixelMapping.containsKey(pixels[i])) {
                    continue;
                }
                pixels[i] = pixelMapping.get(pixels[i]);

            }
            image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        }
    }

    private Palette() {

    }
}
