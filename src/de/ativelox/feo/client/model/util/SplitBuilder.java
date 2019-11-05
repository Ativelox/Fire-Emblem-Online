package de.ativelox.feo.client.model.util;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SplitBuilder {

    private int mTileWidth;
    private int mTileHeight;
    private int mAmount;
    private int mColumnOffset;
    private Function<BufferedImage, BufferedImage> mDirectProcessing;
    private Consumer<BufferedImage> mPostProcessing;
    private int[] mDimensions;

    public SplitBuilder() {
        mColumnOffset = 0;
        mDirectProcessing = new Function<BufferedImage, BufferedImage>() {

            @Override
            public BufferedImage apply(BufferedImage t) {
                return t;
            }
        };

        mPostProcessing = new Consumer<BufferedImage>() {

            @Override
            public void accept(BufferedImage t) {
                return;

            }
        };
        mAmount = 0;
        mTileWidth = 0;
        mTileHeight = 0;
        mDimensions = new int[0];

    }

    public SplitBuilder setPostProcessing(Consumer<BufferedImage> postProcessing) {
        mPostProcessing = postProcessing;
        return this;
    }

    public SplitBuilder setDirectProcessing(Function<BufferedImage, BufferedImage> directProcessing) {
        mDirectProcessing = directProcessing;
        return this;
    }

    public SplitBuilder setColumnOffset(int columnOffset) {
        mColumnOffset = columnOffset;
        return this;

    }

    public SplitBuilder setTileDimensions(int tileWidth, int tileHeight) {
        mTileHeight = tileHeight;
        mTileWidth = tileWidth;
        return this;

    }

    public SplitBuilder setAmount(int amount) {
        mAmount = amount;
        return this;
    }

    public SplitBuilder setDimensions(int... dimensions) {
        mDimensions = dimensions;
        return this;
    }

    public BufferedImage[] apply(final BufferedImage image) {
        return SpriteSheet.splitAndThen(image, mTileWidth, mTileHeight, mAmount, mColumnOffset, mDirectProcessing,
                mPostProcessing, mDimensions);

    }

}
