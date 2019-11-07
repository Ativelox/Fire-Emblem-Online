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

    private boolean mIsReversed;

    public TileEdge(Tile src, Tile dest) {
        mSrc = src;
        mDest = dest;
        mIsReversed = false;
    }

    public void reverse() {
        mIsReversed = !mIsReversed;
    }

    @Override
    public double getCost() {
        return mDest.getCost();
    }

    @Override
    public Tile getDestination() {
        if (mIsReversed) {
            return mSrc;
        }

        return mDest;
    }

    @Override
    public Tile getSource() {
        if (mIsReversed) {
            return mDest;
        }

        return mSrc;
    }

}
