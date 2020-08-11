package de.ativelox.feo.client.model.gfx.tile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class ATileSet<E extends ITile> implements IRequireResource<BufferedImage[]> {

    protected final Map<ETileType, List<E>> mMapping;

    protected int mRow;

    protected final Map<Integer, E> mIdMapping;

    protected final ETileSet mSet;

    private final EResource mResource;

    public ATileSet(final ETileSet set) {
	mMapping = new HashMap<>();
	mRow = 0;
	mSet = set;
	mIdMapping = new HashMap<>();

	switch (set) {
	case FIELDS:
	    mResource = EResource.TILESET_FIELDS;
	    break;
	default:
	    mResource = null;
	    Logger.get().log(ELogType.ERROR, "Couldn't parse the tile set: " + set + " to a proper resource.");
	    break;

	}

    }

    protected void generateTiles(BufferedImage[] tiles) {
	mRow = 0;
	put(0, tiles, ETileType.PLACEHOLDER);
	put(1, tiles, ETileType.CHEST);
	put(2, tiles, ETileType.BRIDGE);
	addRange(3, 4, tiles, ETileType.ARCH);
	put(6, tiles, ETileType.PLAINS);
	addRange(14, 15, tiles, ETileType.PLAINS);
	addRange(20, 29, tiles, ETileType.CLIFF);

	mRow = 1;
	put(0, tiles, ETileType.CLOSED);
	put(1, tiles, ETileType.CHEST);
	put(2, tiles, ETileType.BRIDGE);
	put(6, tiles, ETileType.PLAINS);
	addRange(10, 15, tiles, ETileType.PLAINS);
	addRange(20, 29, tiles, ETileType.CLIFF);

	mRow = 2;
	addRange(0, 3, tiles, ETileType.PLAINS);
	addRange(6, 18, tiles, ETileType.PLAINS);
	addRange(20, 29, tiles, ETileType.CLIFF);

	mRow = 3;
	addRange(2, 4, tiles, ETileType.PLAINS);
	addRange(6, 18, tiles, ETileType.PLAINS);
	addRange(20, 23, tiles, ETileType.CLIFF);
	addRange(26, 29, tiles, ETileType.CLIFF);

	mRow = 4;
	addRange(2, 19, tiles, ETileType.PLAINS);
	addRange(20, 25, tiles, ETileType.CLIFF);
	addRange(29, 30, tiles, ETileType.SEA);

	mRow = 5;
	addRange(2, 19, tiles, ETileType.PLAINS);
	addRange(20, 25, tiles, ETileType.CLIFF);
	addRange(29, 30, tiles, ETileType.SEA);

	mRow = 6;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(20, 21, tiles, ETileType.SEA);
	addRange(22, 27, tiles, ETileType.PLAINS);
	addRange(28, 29, tiles, ETileType.CLIFF);

	mRow = 7;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(20, 21, tiles, ETileType.SEA);
	addRange(22, 27, tiles, ETileType.PLAINS);

	mRow = 8;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(20, 21, tiles, ETileType.SEA);
	addRange(22, 23, tiles, ETileType.PLAINS);
	addRange(24, 27, tiles, ETileType.SEA);

	mRow = 9;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(20, 21, tiles, ETileType.SEA);
	addRange(22, 23, tiles, ETileType.PLAINS);
	addRange(24, 27, tiles, ETileType.SEA);

	mRow = 10;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(20, 21, tiles, ETileType.SEA);
	addRange(24, 26, tiles, ETileType.PLAINS);
	addRange(27, 30, tiles, ETileType.SEA);
	put(31, tiles, ETileType.CLIFF);

	mRow = 11;
	addRange(4, 19, tiles, ETileType.PLAINS);
	addRange(27, 30, tiles, ETileType.SEA);
	put(31, tiles, ETileType.CLIFF);

	mRow = 12;
	addRange(4, 31, tiles, ETileType.PLAINS);

	mRow = 13;
	addRange(0, 16, tiles, ETileType.PEAK);
	addRange(17, 31, tiles, ETileType.PLAINS);

	mRow = 14;
	addRange(0, 17, tiles, ETileType.PEAK);
	addRange(18, 19, tiles, ETileType.PLAINS);
	addRange(20, 25, tiles, ETileType.CLIFF);

	mRow = 15;
	addRange(2, 17, tiles, ETileType.PEAK);
	addRange(18, 19, tiles, ETileType.PLAINS);
	addRange(20, 28, tiles, ETileType.CLIFF);

	mRow = 16;
	addRange(0, 17, tiles, ETileType.PEAK);
	addRange(26, 27, tiles, ETileType.RIVER);

	mRow = 17;
	addRange(0, 17, tiles, ETileType.PEAK);
	addRange(20, 22, tiles, ETileType.RIVER);
	addRange(24, 28, tiles, ETileType.RIVER);

	mRow = 18;
	addRange(0, 17, tiles, ETileType.PEAK);
	addRange(20, 30, tiles, ETileType.RIVER);

	mRow = 19;
	addRange(0, 16, tiles, ETileType.PEAK);
	addRange(20, 28, tiles, ETileType.RIVER);
	put(30, tiles, ETileType.RIVER);

	mRow = 20;
	addRange(0, 5, tiles, ETileType.CLIFF);
	addRange(6, 11, tiles, ETileType.PEAK);
	addRange(12, 14, tiles, ETileType.MOUNTAIN);
	addRange(15, 16, tiles, ETileType.PEAK);
	put(20, tiles, ETileType.RIVER);
	addRange(22, 27, tiles, ETileType.RIVER);
	addRange(30, 31, tiles, ETileType.RIVER);

	mRow = 21;
	addRange(0, 5, tiles, ETileType.CLIFF);
	addRange(6, 11, tiles, ETileType.PEAK);
	addRange(12, 16, tiles, ETileType.MOUNTAIN);
	put(20, tiles, ETileType.RIVER);
	addRange(22, 27, tiles, ETileType.RIVER);
	put(31, tiles, ETileType.RIVER);

	mRow = 22;
	addRange(0, 3, tiles, ETileType.CLIFF);
	addRange(4, 15, tiles, ETileType.PEAK);
	put(16, tiles, ETileType.FOREST);
	addRange(20, 30, tiles, ETileType.ROAD);

	mRow = 23;
	addRange(0, 5, tiles, ETileType.CLIFF);
	addRange(6, 7, tiles, ETileType.PLAINS);
	put(8, tiles, ETileType.PEAK);
	put(9, tiles, ETileType.PLAINS);
	addRange(10, 12, tiles, ETileType.PEAK);
	put(15, tiles, ETileType.PEAK);
	addRange(20, 29, tiles, ETileType.ROAD);

	mRow = 24;
	addRange(0, 5, tiles, ETileType.CLIFF);
	addRange(7, 8, tiles, ETileType.PEAK);
	put(9, tiles, ETileType.PLAINS);
	addRange(9, 10, tiles, ETileType.PEAK);
	addRange(14, 17, tiles, ETileType.FOREST);
	addRange(20, 26, tiles, ETileType.ROAD);

	mRow = 25;
	addRange(0, 1, tiles, ETileType.CLIFF);
	addRange(4, 5, tiles, ETileType.HOUSE);
	addRange(7, 9, tiles, ETileType.PLACEHOLDER);
	addRange(14, 17, tiles, ETileType.FOREST);

	mRow = 26;
	addRange(4, 5, tiles, ETileType.VENDOR);
	addRange(7, 9, tiles, ETileType.PLACEHOLDER);
	addRange(13, 15, tiles, ETileType.PLACEHOLDER);
	addRange(16, 17, tiles, ETileType.FOREST);
	addRange(19, 30, tiles, ETileType.PLACEHOLDER);

	mRow = 27;
	put(6, tiles, ETileType.GATE);
	put(7, tiles, ETileType.PLACEHOLDER);
	put(8, tiles, ETileType.VILLAGE);
	put(9, tiles, ETileType.PLACEHOLDER);
	addRange(13, 15, tiles, ETileType.PLACEHOLDER);
	addRange(16, 18, tiles, ETileType.FOREST);
	addRange(19, 30, tiles, ETileType.PLACEHOLDER);

	mRow = 28;
	addRange(0, 1, tiles, ETileType.PEAK);
	addRange(4, 5, tiles, ETileType.ARMORY);
	put(6, tiles, ETileType.GATE);
	addRange(7, 9, tiles, ETileType.RUINS);
	put(13, tiles, ETileType.PLACEHOLDER);
	put(14, tiles, ETileType.GATE);
	put(15, tiles, ETileType.PLACEHOLDER);
	addRange(16, 18, tiles, ETileType.FOREST);
	put(19, tiles, ETileType.PLACEHOLDER);
	put(20, tiles, ETileType.GATE);
	addRange(21, 22, tiles, ETileType.PLACEHOLDER);
	put(23, tiles, ETileType.GATE);
	addRange(24, 25, tiles, ETileType.PLACEHOLDER);
	put(26, tiles, ETileType.GATE);
	addRange(27, 28, tiles, ETileType.PLACEHOLDER);
	put(29, tiles, ETileType.GATE);
	put(30, tiles, ETileType.PLACEHOLDER);

	mRow = 29;
	put(0, tiles, ETileType.PLAINS);
	put(1, tiles, ETileType.WALL);
	addRange(4, 5, tiles, ETileType.FORT);
	addRange(7, 9, tiles, ETileType.RUINS);
	addRange(10, 15, tiles, ETileType.PLACEHOLDER);
	addRange(16, 18, tiles, ETileType.FOREST);
	addRange(19, 21, tiles, ETileType.PLACEHOLDER);

	mRow = 30;
	put(0, tiles, ETileType.PLAINS);
	put(1, tiles, ETileType.WALL);
	addRange(7, 9, tiles, ETileType.RUINS);
	addRange(10, 15, tiles, ETileType.PLACEHOLDER);
	addRange(19, 21, tiles, ETileType.PLACEHOLDER);

	mRow = 31;
	addRange(0, 1, tiles, ETileType.PEAK);
	addRange(4, 5, tiles, ETileType.ARENA);
	put(10, tiles, ETileType.PLACEHOLDER);
	put(11, tiles, ETileType.GATE);
	addRange(12, 13, tiles, ETileType.PLACEHOLDER);
	put(14, tiles, ETileType.GATE);
	put(15, tiles, ETileType.PLACEHOLDER);
	put(19, tiles, ETileType.PLACEHOLDER);
	put(20, tiles, ETileType.GATE);
	put(21, tiles, ETileType.PLACEHOLDER);

	Tile.resetId();
    }

    protected void addRange(int from, int to, BufferedImage[] tiles, ETileType type) {
	for (int i = from; i <= to; i++) {
	    this.put(i, tiles, type);
	}

    }

    public E get(int index) {
	return mIdMapping.get(index);

    }

    protected abstract void put(int index, BufferedImage[] tiles, ETileType type);

    @Override
    public BufferedImage[] request() {
	return Assets.getFor(this);

    }

    @Override
    public EResource getResourceTypes() {
	return mResource;
    }

    public ETileSet getTileSet() {
	return mSet;
    }
}
