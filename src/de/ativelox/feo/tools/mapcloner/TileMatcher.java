/**
 * 
 */
package de.ativelox.feo.tools.mapcloner;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.tile.ETileSet;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.gfx.tile.OpenTileSet;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.util.SpriteSheet;

/**
 * @author Julian Tischner <juliantischner97@gmail.com>
 *
 */
public class TileMatcher {

    private final OpenTileSet mTileSet;

    private final List<Tile> mTiles;

    private static final Path MAP_DUMP_PATH = Paths.get("res", "dumps", "fe6", "map");

    private final ETileSet mTileSetType;

    public TileMatcher(ETileSet tileSet) {
	mTileSet = new OpenTileSet(tileSet);
	mTiles = new ArrayList<>();
	mTileSetType = tileSet;

	initialize();

    }

    private void initialize() {
	mTileSet.getTiles().values().forEach(s -> s.forEach(p -> mTiles.add(p)));

    }

    public void matchAgainst(String fileName) {
	BufferedImage image = SpriteSheet.load(MAP_DUMP_PATH.resolve(fileName)).get();
	BufferedImage[] tiles = SpriteSheet.smartSplit(image, 16, 16);

	int mapWidth = image.getWidth() / 16;
	int mapHeight = image.getHeight() / 16;

	ITile[][] result = new Tile[mapWidth][mapHeight];

	for (int i = 0; i < tiles.length; i++) {
	    long bestScore = Long.MAX_VALUE;
	    for (final Tile tile : mTiles) {
		long score = 0;

		for (int x = 0; x <= 15; x++) {
		    for (int y = 0; y <= 15; y++) {
			score += Math.abs((tiles[i].getRGB(x, y) - tile.getImage().getRGB(x, y)));

		    }
		}
		if (bestScore > score) {
		    bestScore = score;
		    result[i % mapWidth][(int) Math.floor(i / (float) mapWidth)] = tile;

		}
	    }
	}
	Assets.saveMap(result, mTileSetType);
    }
}
