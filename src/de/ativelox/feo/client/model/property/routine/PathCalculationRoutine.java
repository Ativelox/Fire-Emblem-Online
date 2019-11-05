package de.ativelox.feo.client.model.property.routine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EIndicatorDirection;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;
import de.ativelox.feo.util.Pair;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.algorithms.Path;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class PathCalculationRoutine {

    private Set<Tile> mInRange;

    private MovementRange mMovementRange;

    private MovementIndicator mMovementIndicator;

    private final Map mMap;

    private Path<Tile, Edge<Tile>> mLast;

    private Path<Tile, Edge<Tile>> mCurrentPath;

    private IUnit mActor;

    private boolean mIsActive;

    private int mCursorX;

    private int mCursorY;

    private Pair<Integer, Integer> mStart;

    public PathCalculationRoutine(final Map map) {
        mMap = map;

        mMovementIndicator = new MovementIndicator();

    }

    public boolean isActive() {
        return mIsActive;
    }

    public void startCalculation(IUnit unit) {
        // TODO: optimize, no need to execute dijkstra's twice.

        mStart = Pair.of(unit.getX(), unit.getY());

        mActor = unit;

        mInRange = mMap.getTilesInRange(unit, unit.getMovement());

        Set<Tile> ranged = mMap.getTilesInRange(unit, unit.getMovement() + unit.getRange());

        ranged.removeAll(mInRange);

        mIsActive = true;

        mMovementRange = new MovementRange(mInRange, mMap.getAttackableTilesInRange(mInRange, unit.getRange()));

    }

    public void stop() {
        mInRange = null;
        mCurrentPath = null;
        mIsActive = false;
        mActor = null;
        mMovementRange = null;
        mMovementIndicator.set(new ArrayList<>());
        mStart = null;

    }

    public MovementRange getMovementRange() {
        if (!isActive()) {
            return new MovementRange(new HashSet<>(), new HashSet<>());
        }
        return mMovementRange;
    }

    public MovementIndicator getMovementIndicator() {
        return mMovementIndicator;

    }

    public Optional<Path<Tile, Edge<Tile>>> get(Tile tile) {
        if (mInRange == null) {
            return Optional.empty();
        }
        return mMap.getPath(mActor, tile, mActor.getMovement());

    }

    public void returnToLast() {
        if (mStart == null) {
            return;
        }
        mActor.moveInstantly(mStart.getFirst(), mStart.getSecond());

    }

    public IUnit getActor() {
        return mActor;
    }

    public void updateCursorLocation(ISpatial cursor) {

        mCursorX = cursor.getX();
        mCursorY = cursor.getY();

        if (mInRange == null) {
            return;
        }
        Optional<Path<Tile, Edge<Tile>>> tiles = mMap.getPath(mActor, mMap.getByPos(cursor.getX(), cursor.getY()),
                mActor.getMovement());

        if (tiles.isEmpty() && mLast == null) {
            return;
        }

        if (tiles.isEmpty()) {
            mCurrentPath = mLast;

        } else {
            mCurrentPath = tiles.get();
            mLast = tiles.get();

        }

        Iterator<EdgeCost<Tile, Edge<Tile>>> pathIterator = mCurrentPath.iterator();

        if (!pathIterator.hasNext()) {
            return;
        }

        List<Pair<ISpatial, EIndicatorDirection>> result = new ArrayList<>();

        ISpatial last = null;
        ISpatial current = mActor;
        ISpatial next = pathIterator.next().getEdge().getDestination();

        result.add(Pair.of(current, getDirection(last, current, next)));

        while (pathIterator.hasNext()) {
            last = current;
            current = next;
            next = pathIterator.next().getEdge().getDestination();

            result.add(Pair.of(current, getDirection(last, current, next)));

        }
        result.add(Pair.of(next, getDirection(current, next, null)));

        mMovementIndicator.set(result);

    }

    private EIndicatorDirection getDirection(ISpatial last, ISpatial current, ISpatial next) {

        EIndicatorDirection direction = null;

        if (last == null) {

            if (current.getX() > next.getX()) {
                direction = EIndicatorDirection.BEGINNING_RIGHT_LEFT;

            } else if (current.getX() < next.getX()) {
                direction = EIndicatorDirection.BEGINNING_LEFT_RIGHT;

            } else if (current.getY() < next.getY()) {
                direction = EIndicatorDirection.BEGINNING_UP_DOWN;

            } else if (current.getY() > next.getY()) {
                direction = EIndicatorDirection.BEGINNING_DOWN_UP;

            }
        } else if (next == null) {
            if (current.getX() > last.getX()) {
                direction = EIndicatorDirection.END_LEFT_RIGHT;

            } else if (current.getX() < last.getX()) {
                direction = EIndicatorDirection.END_RIGHT_LEFT;

            } else if (current.getY() < last.getY()) {
                direction = EIndicatorDirection.END_DOWN_UP;

            } else if (current.getY() > last.getY()) {
                direction = EIndicatorDirection.END_UP_DOWN;

            }

        } else {
            if (current.getX() == last.getX() && current.getX() == next.getX()) {
                direction = EIndicatorDirection.VERTICAL;

            } else if (current.getY() == last.getY() && current.getY() == next.getY()) {
                direction = EIndicatorDirection.HORIZONTAL;

            }

            else if (last.getX() == current.getX() && last.getY() > current.getY() && current.getX() < next.getX()) {
                direction = EIndicatorDirection.ARC_DOWN_RIGHT;

            } else if (last.getX() == current.getX() && last.getY() > current.getY() && current.getX() > next.getX()) {
                direction = EIndicatorDirection.ARC_LEFT_DOWN;

            } else if (last.getX() == current.getX() && last.getY() < current.getY() && current.getX() > next.getX()) {
                direction = EIndicatorDirection.ARC_LEFT_UP;

            } else if (last.getX() == current.getX() && last.getY() < current.getY() && current.getX() < next.getX()) {
                direction = EIndicatorDirection.ARC_UP_RIGHT;

            }

            else if (last.getY() == current.getY() && last.getX() > current.getX() && current.getY() > next.getY()) {
                direction = EIndicatorDirection.ARC_UP_RIGHT;

            } else if (last.getY() == current.getY() && last.getX() > current.getX() && current.getY() < next.getY()) {
                direction = EIndicatorDirection.ARC_DOWN_RIGHT;

            } else if (last.getY() == current.getY() && last.getX() < current.getX() && current.getY() < next.getY()) {
                direction = EIndicatorDirection.ARC_LEFT_DOWN;

            } else if (last.getY() == current.getY() && last.getX() < current.getX() && current.getY() > next.getY()) {
                direction = EIndicatorDirection.ARC_LEFT_UP;
            }
        }

        if (direction == null) {
            Logger.get().log(ELogType.ERROR, "Couldn't find an appropriate direction for the indicator.");
        }
        return direction;
    }

    public void executeIfValid() {
        if (isActive() && mCurrentPath != null) {
            mActor.move(mCurrentPath);
        }
    }
}
