package de.ativelox.feo.client.model.util;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EDirection;
import de.ativelox.feo.client.model.property.ICanMove;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SmoothMoveRoutine implements IMoveRoutine {

    private final ICanMove mToMove;
    private static final float SPEED = 3;

    private int mLastX;
    private int mLastY;

    private Iterator<EdgeCost<Tile, Edge<Tile>>> mPath;

    private Tile mCurrent;

    private EDirection mLastDirection;

    private boolean mReversed;

    private int mLimit;

    private int mMovedTiles;

    public SmoothMoveRoutine(ICanMove caller) {
        mToMove = caller;
    }

    @Override
    public void move(Iterator<EdgeCost<Tile, Edge<Tile>>> path, boolean reversed, int limit) {
        mLastDirection = EDirection.RIGHT;
        mPath = path;
        mLastX = mToMove.getX();
        mLastY = mToMove.getY();
        mReversed = reversed;
        mLimit = limit;

        if (!mPath.hasNext()) {
            mPath = null;
            mToMove.onMoveFinished();
            return;
        }

        if (reversed) {
            mCurrent = mPath.next().getEdge().getSource();
        } else {

            mCurrent = mPath.next().getEdge().getDestination();
        }

        mToMove.onDirectionChange(mLastDirection);

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mPath == null) {
            return;
        }

        int destX = mCurrent.getX();
        int destY = mCurrent.getY();

        EDirection dir;

        int distanceX = 0;
        int distanceY = 0;

        if (mToMove.getX() > destX) {
            dir = EDirection.LEFT;
            distanceX = destX - mLastX;

        } else if (mToMove.getX() < destX) {
            dir = EDirection.RIGHT;
            distanceX = destX - mLastX;

        } else if (mToMove.getY() < destY) {
            dir = EDirection.DOWN;
            distanceY = destY - mLastY;

        } else {
            dir = EDirection.UP;
            distanceY = destY - mLastY;

        }

        if (dir != mLastDirection) {
            mToMove.onDirectionChange(dir);
        }

        distanceX *= (1f / ts.getPassed()) * SPEED;
        distanceY *= (1f / ts.getPassed()) * SPEED;

        mToMove.setX(mToMove.getX() + distanceX);
        mToMove.setY(mToMove.getY() + distanceY);

        boolean reachedTempGoal = false;

        if (dir == EDirection.UP && mToMove.getY() <= destY) {
            mToMove.setY(destY);
            reachedTempGoal = true;

        } else if (dir == EDirection.DOWN && mToMove.getY() >= destY) {
            mToMove.setY(destY);
            reachedTempGoal = true;

        } else if (dir == EDirection.LEFT && mToMove.getX() <= destX) {
            mToMove.setX(destX);
            reachedTempGoal = true;

        } else if (dir == EDirection.RIGHT && mToMove.getX() >= destX) {
            mToMove.setX(destX);
            reachedTempGoal = true;

        }

        if (reachedTempGoal) {
            mMovedTiles++;
            
            System.out.println("REACHED TEMP GOAL " + mMovedTiles + " " + mPath.hasNext());

            if (!mPath.hasNext() || mMovedTiles >= mLimit) {
                mPath = null;
                mToMove.onMoveFinished();
                mMovedTiles = 0;
                return;
            }
            if (mReversed) {

                mCurrent = mPath.next().getEdge().getSource();
            } else {
                mCurrent = mPath.next().getEdge().getDestination();
            }
            mLastX = mToMove.getX();
            mLastY = mToMove.getY();

        }
        mLastDirection = dir;

    }
}
