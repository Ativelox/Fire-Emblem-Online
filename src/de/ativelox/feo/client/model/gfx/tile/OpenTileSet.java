/**
 * 
 */
package de.ativelox.feo.client.model.gfx.tile;

import java.util.List;
import java.util.Map;

/**
 * @author Julian Tischner <juliantischner97@gmail.com>
 *
 */
public class OpenTileSet extends TileSet {

    public OpenTileSet(ETileSet set) {
	super(set);

    }

    public Map<ETileType, List<Tile>> getTiles() {
	return mMapping;
    }
}
