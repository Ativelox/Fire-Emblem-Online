package de.ativelox.feo.client.model.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SpriteSheet {

    private SpriteSheet() {

    }

    public static Optional<BufferedImage> load(Path path) {
        try {
            return Optional.of(ImageIO.read(Files.newInputStream(path, StandardOpenOption.READ)));

        } catch (IOException e) {
            Logger.get().logError("Couldn't parse the image located at: " + path, e);

        }
        return Optional.empty();

    }

    public static Image[] split(BufferedImage image, int tileWidth, int tileHeight, int amount, int columnOffset) {
        int tilesPerRow = amount / (tileWidth * 2);
        int tilesPerColumn = amount / (tileHeight * 2);

        int[] dimensions = new int[tilesPerColumn];

        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = tilesPerRow;
        }
        return split(image, tileWidth, tileHeight, amount, columnOffset, dimensions);

    }

    /**
     * Splits the given image into tiles specified by <tt>tileWidth</tt> and
     * <tt>tileHeight</tt>. Splits the image starting from top-left to bottom-right,
     * going in a constant right fashion.
     * 
     * @param image        The image to split into tiles.
     * @param tileWidth    The width of each tile, needs to be consistent.
     * @param tileHeight   The height of each tile, needs to be consistent.
     * @param amount       The amount of tiles to create from the image, needs to be
     *                     consistent with <tt>dimensions</tt>.
     * @param columnOffset The offset for the columns, setting this to one will
     *                     ignore the first column of tiles for example.
     * @param dimensions   Describes how many tiles are present in each row, where
     *                     <tt>dimensions[0]</tt> describes the top most row.
     * @return All the tiles in the order specified.
     */
    public static Image[] split(BufferedImage image, int tileWidth, int tileHeight, int amount, int columnOffset,
            int... dimensions) {
        Image[] result = new Image[amount];

        int row = 0;
        int i = 0;
        for (int columnAmount : dimensions) {
            for (int column = columnOffset; column < columnOffset + columnAmount; column++) {
                result[i] = image.getSubimage(tileWidth * column, tileHeight * row, tileWidth, tileHeight);
                i++;

            }
            row++;
        }
        return result;
    }

    private static void write(BufferedImage image, Path path) {

        try {
            Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
            Files.createFile(path);
            ImageIO.write(image, "png", Files.newOutputStream(path));

        } catch (IOException e) {
            Logger.get().logError(e);

        }
    }

    public static void generateFrom(Path from, Path to, int tileWidth, int tileHeight) {
        generateFrom(from, to, tileWidth, tileHeight, new Color(168, 208, 160));

    }

    public static void generateFrom(Path from, Path to, int tileWidth, int tileHeight, Color transparent) {
        BufferedImage image = null;

        try {
            int amount = (int) Files.list(from).count();

            Iterator<BufferedImage> imageIterator = Files.list(from).sorted(new FileNameComparator())
                    .map(path -> SpriteSheet.load(path)).filter(opt -> opt.isPresent()).map(opt -> opt.get())
                    .iterator();

            int columns = (int) Math.ceil(Math.sqrt(amount));
            int rows = (int) Math.floor(Math.sqrt(amount));

            if (amount < 4) {
                columns = 2;
                rows = 2;
            }
            image = new BufferedImage(tileWidth * columns, tileHeight * rows, BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g = image.createGraphics();

            for (int row = 0; row <= rows; row++) {
                for (int column = 0; column < columns; column++) {
                    if (!imageIterator.hasNext()) {
                        break;

                    }
                    g.drawImage(imageIterator.next().getSubimage(0, 0, tileWidth, tileHeight), tileWidth * column,
                            tileHeight * row, tileWidth, tileHeight, null);

                }
            }
            g.dispose();

        } catch (IOException e) {
            Logger.get().logError(e);
            return;

        }
        ImageProducer ip = new FilteredImageSource(image.getSource(), new TransparencyFilter(transparent));
        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        transparentImage.createGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(ip), 0, 0, null);
        SpriteSheet.write(transparentImage, to);

    }

    public static BufferedImage stitchHorizontally(List<Image> toStich) {
        if (toStich.size() <= 0) {
            Logger.get().log(ELogType.ERROR, "There were no images to stitch...");
            return null;
        }
        Image sample = toStich.get(0);

        int newWidth = sample.getWidth(null) * toStich.size();
        int newHeight = sample.getHeight(null);

        BufferedImage result = new BufferedImage(newWidth * Display.INTERNAL_RES_FACTOR,
                newHeight * Display.INTERNAL_RES_FACTOR, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = result.createGraphics();

        int i = 0;
        for (final Image image : toStich) {
            g.drawImage(image, i * image.getWidth(null) * Display.INTERNAL_RES_FACTOR, 0,
                    image.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                    image.getHeight(null) * Display.INTERNAL_RES_FACTOR, null);
            i++;
        }
        return result;

    }

    public static BufferedImage stitchVertically(List<Image> toStitch) {
        if (toStitch.size() <= 0) {
            Logger.get().log(ELogType.ERROR, "There were no images to stitch...");
            return null;
        }
        int newWidth = 0;
        int newHeight = 0;

        for (final Image image : toStitch) {
            newWidth += image.getWidth(null);
            newHeight += image.getHeight(null);

        }

        BufferedImage result = new BufferedImage(newWidth * Display.INTERNAL_RES_FACTOR,
                newHeight * Display.INTERNAL_RES_FACTOR, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = result.createGraphics();

        int i = 0;
        int lastHeight = 0;
        for (final Image image : toStitch) {

            if (i > 0) {
                lastHeight += toStitch.get(i - 1).getHeight(null) * Display.INTERNAL_RES_FACTOR;
            }
            System.out.println(lastHeight);

            g.drawImage(image, 0, lastHeight, image.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                    image.getHeight(null) * Display.INTERNAL_RES_FACTOR, null);
            i++;
        }
        g.dispose();

        return result;
    }

    public static BufferedImage applyTransparency(BufferedImage image, int alpha, boolean[] ignore) {
        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

        for (int i = 0; i < pixels.length; i++) {
            if (ignore[i]) {
                continue;
            }
            Color c = new Color(pixels[i]);

            c = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
            pixels[i] = c.getRGB();

        }
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        return image;

    }
}
