package de.ativelox.feo.client.model.util;

import java.util.Deque;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EDirection;
import de.ativelox.feo.client.model.property.ICanMove;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SmoothMoveRoutine implements IMoveRoutine {

    private final ICanMove mToMove;
    private static final float SPEED = 3;

    private int mLastX;
    private int mLastY;

    private Deque<Tile> mPath;

    private EDirection mLastDirection;

    public SmoothMoveRoutine(ICanMove caller) {
        mToMove = caller;
    }

    @Override
    public void move(Deque<Tile> path) {
        mLastDirection = EDirection.RIGHT;
        mPath = path;
        mLastX = mToMove.getX();
        mLastY = mToMove.getY();

        mToMove.onDirectionChange(mLastDirection);

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mPath == null) {
            return;
        }
        int destX = mPath.peek().getX();
        int destY = mPath.peek().getY();

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
            if (mPath.size() <= 1) {
                mPath = null;
                mToMove.onMoveFinished();
                return;
            }
            mPath.pop();
            mLastX = mToMove.getX();
            mLastY = mToMove.getY();

        }

        mLastDirection = dir;

    }
}
