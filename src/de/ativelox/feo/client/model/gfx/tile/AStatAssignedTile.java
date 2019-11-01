package de.ativelox.feo.client.model.gfx.tile;

import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AStatAssignedTile extends SpatialObject implements ITile {

    public static final int WIDTH = 16 * Display.INTERNAL_RES_FACTOR;
    public static final int HEIGHT = 16 * Display.INTERNAL_RES_FACTOR;
    
    protected final ETileType mType;

    private int mCost;
    private int mAvoidance;
    private int mHealing;

    public AStatAssignedTile(ETileType type) {
        super(0, 0, WIDTH, HEIGHT);

        mType = type;

        mCost = 1;
        mAvoidance = 0;
        mHealing = 0;

        this.applyStats(type);
    }

    @Override
    public int getCost() {
        // TODO Auto-generated method stub
        return mCost;
    }

    @Override
    public int getAvoidance() {
        // TODO Auto-generated method stub
        return mAvoidance;
    }

    @Override
    public int getHealing() {
        // TODO Auto-generated method stub
        return mHealing;
    }

    private void applyStats(ETileType type) {
        switch (type) {
        case PLAINS:
        case ROAD:
        case BRIDGE:
        case CHEST:
        case FLOOR:
        case STAIRS:
        case GLACIER:
            break;

        case VILLAGE:
            mAvoidance = 10;
        case CLOSED:
        case HOUSE:
        case ARMORY:
        case VENDOR:
        case ARENA:
            break;

        case FORT:
            mCost = 2;
            mAvoidance = 20;
            mHealing = 20;
            break;

        case GATE:
            mHealing = 20;
            mAvoidance = 10;
            break;

        case FOREST:
            mCost = 2;
            mAvoidance = 20;
        case PILLAR:
            break;

        case WOODS:
            mCost = 255;
            mAvoidance = 30;
            break;

        case SAND:
            mAvoidance = 5;
            break;

        case DESERT:
            mCost = 2;
            mAvoidance = 5;
        case ARCH:
            break;

        case RIVER:
            mCost = 5;
            break;

        case MOUNTAIN:
            mCost = 4;
            mAvoidance = 30;
            break;

        case PEAK:
            mAvoidance = 40;
            mCost = 255;
            break;

        case SEA:
            mAvoidance = 10;
            mCost = 255;
        case LAKE:
            break;

        case WALL:
            mAvoidance = 20;
            mCost = 255;
            break;

        case DOOR:
            mCost = 255;
        case ROOF:
        case CLIFF:
            break;

        case THRONE:
            mCost = 2;
            mAvoidance = 30;
            mHealing = 10;
            break;

        case RUINS:
            mCost = 2;
            break;

        case PLACEHOLDER:
            mCost = 255;
            break;

        default:
            Logger.get().log(ELogType.ERROR, "Couldn't parse stats for the following tile: " + type);
        }

    }

    @Override
    public abstract int getId();
}
