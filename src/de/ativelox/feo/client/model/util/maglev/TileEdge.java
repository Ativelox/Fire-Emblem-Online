package de.ativelox.feo.client.model.util.maglev;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TileEdge implements Edge<Tile> {

    private final Tile mSrc;

    private final Tile mDest;

    public TileEdge(Tile src, Tile dest) {
        mSrc = src;
        mDest = dest;
    }

    @Override
    public double getCost() {
        return mDest.getCost();
    }

    @Override
    public Tile getDestination() {
        return mDest;
    }

    @Override
    public Tile getSource() {
        return mSrc;
    }

}
