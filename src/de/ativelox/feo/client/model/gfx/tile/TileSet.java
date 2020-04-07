package de.ativelox.feo.client.model.gfx.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TileSet extends ATileSet<Tile> {

    public TileSet(ETileSet set) {
	super(set);

	generateTiles(request());

    }

    @Override
    protected void put(int index, BufferedImage[] tiles, ETileType type) {
	if (!mMapping.containsKey(type)) {
	    mMapping.put(type, new ArrayList<>());

	}
	Tile tile = new Tile(type, tiles[index + (32 * mRow)]);

	mMapping.get(type).add(tile);
	mIdMapping.put(tile.getId(), tile);

    }
}
