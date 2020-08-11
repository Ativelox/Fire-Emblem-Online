package de.ativelox.feo.client.model.gfx.tile;

import java.awt.image.BufferedImage;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.ILayerable;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Tile extends AStatAssignedTile implements ISpatial, IRenderable, ILayerable {

    protected final BufferedImage mImage;

    private static int id = 0;

    protected final int mId;

    private int mLayer;

    public Tile(ETileType type, BufferedImage image, int id) {
	super(type);

	mId = id;

	mImage = image;
    }

    public Tile(ETileType type, BufferedImage image) {
	super(type);

	mId = Tile.id;

	Tile.id++;

	mImage = image;
    }

    public BufferedImage getImage() {
	return mImage;
    }

    public static void resetId() {
	id = 0;
    }

    public int getId() {
	return mId;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
	g.drawImage(mImage, mX, mY, mWidth, mHeight, getLayer());

    }

    @Override
    public int getLayer() {
	return mLayer;
    }

    @Override
    public void setLayer(int layer) {
	mLayer = layer;

    }

    @Override
    public ITile copy() {
	return new Tile(mType, mImage, this.mId);
    }
}
