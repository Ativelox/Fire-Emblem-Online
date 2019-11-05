package de.ativelox.feo.client.model.util;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EDirection;
import de.ativelox.feo.client.model.property.ICanMove;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.algorithms.Path;
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

    public SmoothMoveRoutine(ICanMove caller) {
        mToMove = caller;
    }

    @Override
    public void move(Path<Tile, Edge<Tile>> path) {
        mLastDirection = EDirection.RIGHT;
        mPath = path.iterator();
        mLastX = mToMove.getX();
        mLastY = mToMove.getY();

        mCurrent = mPath.next().getEdge().getDestination();

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
            if (!mPath.hasNext()) {
                mPath = null;
                mToMove.onMoveFinished();
                return;
            }
            mCurrent = mPath.next().getEdge().getDestination();
            mLastX = mToMove.getX();
            mLastY = mToMove.getY();

        }
        mLastDirection = dir;

    }
}
