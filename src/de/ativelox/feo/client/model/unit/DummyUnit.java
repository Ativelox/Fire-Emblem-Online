package de.ativelox.feo.client.model.unit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EDirection;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.callback.IMoveFinishedListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.util.IMoveRoutine;
import de.ativelox.feo.client.model.util.SmoothMoveRoutine;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DummyUnit extends SpatialObject implements IUnit, IRequireResources, ICanMove {

    private IAnimation mHover;
    private IAnimation mSelection;

    private IAnimation mMoveLeft;
    private IAnimation mMoveRight;
    private IAnimation mMoveDown;
    private IAnimation mMoveUp;
    private IAnimation mCurrentAnimation;

    private IMoveRoutine mMover;

    private boolean mIsSelected;

    private final List<ISelectionListener> mSelectionListener;
    private final List<IMoveFinishedListener> mMoveListener;

    private final String mDataName;

    public DummyUnit(int x, int y, String dataName) {
        super(Tile.WIDTH * x, Tile.HEIGHT * y, Tile.WIDTH, Tile.HEIGHT);

        mMoveListener = new ArrayList<>();
        mSelectionListener = new ArrayList<>();
        mDataName = dataName;
        mMover = new SmoothMoveRoutine(this);

        load();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mCurrentAnimation.render(g);

    }

    @Override
    public void update(TimeSnapshot ts) {
        mMover.update(ts);

        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());

        mCurrentAnimation.update(ts);

    }

    @Override
    public void load() {
        mHover = Assets.getFor(EResource.MAP_HOVER, mDataName);
        mMoveRight = Assets.getFor(EResource.MAP_MOVE_RIGHT, mDataName);
        mMoveLeft = Assets.getFor(EResource.MAP_MOVE_LEFT, mDataName);
        mMoveUp = Assets.getFor(EResource.MAP_MOVE_UP, mDataName);
        mMoveDown = Assets.getFor(EResource.MAP_MOVE_DOWN, mDataName);
        mSelection = Assets.getFor(EResource.MAP_SELECTION, mDataName);

        mCurrentAnimation = mHover;

        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());

        mCurrentAnimation.start();
    }

    @Override
    public void move(Deque<Tile> path) {
        mMover.move(path);

    }

    @Override
    public void onDirectionChange(EDirection direction) {
        switch (direction) {
        case DOWN:
            mCurrentAnimation = mMoveDown;
            break;
        case LEFT:
            mCurrentAnimation = mMoveLeft;
            break;
        case RIGHT:
            mCurrentAnimation = mMoveRight;
            break;
        case UP:
            mCurrentAnimation = mMoveUp;
            break;
        default:
            break;

        }
        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());
        mCurrentAnimation.reset();
        mCurrentAnimation.start();

    }

    @Override
    public void onMoveFinished() {
        mCurrentAnimation = mHover;
        mCurrentAnimation.reset();
        mCurrentAnimation.start();

        mMoveListener.forEach(l -> l.onMoveFinished(this));

    }

    @Override
    public void selected() {
        mIsSelected = true;
        mCurrentAnimation = mSelection;
        mCurrentAnimation.reset();
        mCurrentAnimation.start();

        mSelectionListener.forEach(l -> l.onSelect(this));

    }

    @Override
    public void deSelected() {
        mIsSelected = false;
        mCurrentAnimation = mHover;
        mCurrentAnimation.reset();
        mCurrentAnimation.start();

        mSelectionListener.forEach(l -> l.onDeSelect(this));

    }

    @Override
    public boolean isSelected() {
        return mIsSelected;
    }

    @Override
    public void add(ISelectionListener listener) {
        mSelectionListener.add(listener);

    }

    @Override
    public void remove(ISelectionListener listener) {
        mSelectionListener.add(listener);

    }

    @Override
    public void addMoveFinishedListener(IMoveFinishedListener listener) {
        mMoveListener.add(listener);

    }

    @Override
    public void removeMoveFinishedListener(IMoveFinishedListener listener) {
        mMoveListener.remove(listener);

    }
}
