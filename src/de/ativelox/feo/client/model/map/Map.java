package de.ativelox.feo.client.model.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.model.util.closy.ManhattanDistance;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;
import de.zabuza.closy.external.NearestNeighborComputation;
import de.zabuza.closy.external.NearestNeighborComputations;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Map extends SpatialObject implements IRequireResource<Tile[][]>, IRenderable, IUpdateable {

    public final String mFileName;

    private Tile[][] mInternalMap;

    private boolean mIsLoaded;

    protected final List<IUnit> mAlliedUnits;

    protected final List<IUnit> mOpposedUnits;

    private NearestNeighborComputation<IUnit> mNearestOpposed;
    private NearestNeighborComputation<IUnit> mNearestAllied;

    public Map(String fileName, int x, int y) {
        super(x, y, 0, 0);
        mNearestOpposed = NearestNeighborComputations.of(new ManhattanDistance<>());
        mNearestAllied = NearestNeighborComputations.of(new ManhattanDistance<>());

        mAlliedUnits = new ArrayList<>();
        mOpposedUnits = new ArrayList<>();

        mFileName = fileName;

    }

    public Tile[][] getInternalMap() {
        return mInternalMap;

    }

    public void add(IUnit unit) {
        if (unit.getAffiliation() == EAffiliation.ALLIED) {
            mAlliedUnits.add(unit);

        } else {
            mOpposedUnits.add(unit);

        }
    }

    public void remove(IUnit unit) {
        if (unit.getAffiliation() == EAffiliation.ALLIED) {
            mAlliedUnits.remove(unit);
        } else {
            mOpposedUnits.remove(unit);
        }
    }

    public void addAll(Collection<IUnit> units) {
        for (final IUnit unit : units) {
            if (unit.getAffiliation() == EAffiliation.ALLIED) {
                mAlliedUnits.add(unit);

            } else {
                mOpposedUnits.add(unit);

            }
        }
    }

    public void load() {
        mInternalMap = request();
        for (int i = 0; i < mInternalMap.length; i++) {
            for (int j = 0; j < mInternalMap[i].length; j++) {
                if (mInternalMap[i][j] == null) {
                    continue;

                }
                Tile tile = mInternalMap[i][j];

                tile.setX(j * tile.getWidth());
                tile.setY(i * tile.getHeight());

            }
        }
        setWidth(mInternalMap[0].length * mInternalMap[0][0].getWidth());
        setHeight(mInternalMap.length * mInternalMap[0][0].getHeight());

        mIsLoaded = true;
    }

    @Override
    public Tile[][] request() {
        return Assets.getFor(this, mFileName);
    }

    @Override
    public EResource getResourceTypes() {
        return EResource.MAP;

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (!mIsLoaded) {
            return;
        }
        mAlliedUnits.forEach(u -> u.update(ts));
        mOpposedUnits.forEach(u -> u.update(ts));

        // TODO: only recreate on unit movement.
        mNearestAllied = NearestNeighborComputations.of(new ManhattanDistance<>());
        mNearestOpposed = NearestNeighborComputations.of(new ManhattanDistance<>());

        mAlliedUnits.forEach(u -> mNearestAllied.add(u));
        mOpposedUnits.forEach(u -> mNearestOpposed.add(u));
    }

    public Tile getByIndex(int indexX, int indexY) {
        return mInternalMap[indexY][indexX];
    }

    public Tile getByPos(int x, int y) {
        int indexX = (int) Math.floor(x / Tile.WIDTH);
        int indexY = (int) Math.floor(y / Tile.HEIGHT);

        if (indexX < 0 || indexY < 0 || indexX > mInternalMap[0].length - 1 || indexY > mInternalMap.length - 1) {
            Logger.get().log(ELogType.ERROR,
                    "Couldn't parse: (" + indexX + ", " + indexY + ") to internal coordinates.");

        }
        return getByIndex(indexX, indexY);

    }

    public Collection<IUnit> getAlliesInRange(IUnit unit, int range) {

        Collection<IUnit> result = new ArrayList<>();

        if (unit.getAffiliation() == EAffiliation.ALLIED) {
            result = mNearestAllied.getNeighborhood(unit, range);

        } else {
            result = mNearestOpposed.getNeighborhood(unit, range);
        }
        result.remove(unit);
        return result;
    }

    public Collection<IUnit> getOpponentsInRange(IUnit unit, int range) {
        if (unit.getAffiliation() == EAffiliation.ALLIED) {
            return mNearestOpposed.getNeighborhood(unit, range);

        } else {
            return mNearestAllied.getNeighborhood(unit, range);

        }
    }

    public boolean allyInRange(IUnit unit, int range) {
        return getAlliesInRange(unit, range).size() > 0;

    }

    public boolean opponentInRange(IUnit unit, int range) {
        return getOpponentsInRange(unit, range).size() > 0;

    }

    public Optional<IUnit> isOccupied(int x, int y) {
        for (final IUnit unit : mAlliedUnits) {
            if (unit.getX() == x && unit.getY() == y) {
                return Optional.of(unit);
            }
        }
        for (final IUnit unit : mOpposedUnits) {
            if (unit.getX() == x && unit.getY() == y) {
                return Optional.of(unit);
            }
        }
        return Optional.empty();
    }

    public boolean isOccupied(Tile tile) {
        return isOccupied(tile.getX(), tile.getY()).isPresent();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (!mIsLoaded) {
            return;
        }

        for (int i = 0; i < mInternalMap.length; i++) {
            for (int j = 0; j < mInternalMap[i].length; j++) {

                if (mInternalMap[i][j] == null) {
                    continue;

                }
                mInternalMap[i][j].render(g);

            }
            g.drawLine(new Color(0, 0, 0, 50), 0, i * Tile.WIDTH, getWidth(), i * Tile.WIDTH, 5);

        }
        for (int j = 0; j < mInternalMap[0].length; j++) {
            g.drawLine(new Color(0, 0, 0, 50), j * Tile.HEIGHT, 0, j * Tile.HEIGHT, getHeight(), 5);

        }

        mAlliedUnits.forEach(u -> u.render(g));
        mOpposedUnits.forEach(u -> u.render(g));

    }

    public List<IUnit> getAlliedUnits() {
        return mAlliedUnits;

    }

    public List<IUnit> getOpposedUnits() {
        return mOpposedUnits;
    }
}
