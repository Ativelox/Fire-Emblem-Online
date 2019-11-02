package de.ativelox.feo.client.model.unit;

import java.awt.Color;
import java.awt.Image;
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
import de.ativelox.feo.client.model.property.callback.IMoveListener;
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

    private final String mPortraitName;
    private Image mPortrait;

    private final String mName;

    private IMoveRoutine mMover;

    private boolean mIsSelected;

    private final List<ISelectionListener> mSelectionListener;
    private final List<IMoveListener> mMoveListener;

    private final String mDataName;

    private final int mMovement;

    private int mRange;

    private boolean mIsWaiting;

    public DummyUnit(int x, int y, int movement, String dataName, String portraitName, String name) {
        super(Tile.WIDTH * x, Tile.HEIGHT * y, Tile.WIDTH, Tile.HEIGHT);

        mName = name;

        mMoveListener = new ArrayList<>();
        mSelectionListener = new ArrayList<>();
        mDataName = dataName;
        mMover = new SmoothMoveRoutine(this);

        mPortraitName = portraitName;

        mMovement = movement;

        mRange = 1;

        load();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mCurrentAnimation.render(g);
        if (mIsWaiting) {
            // TODO: debugging tool for now, since no palette change is supported atm.
            g.fillRect(new Color(0, 0, 0, 150), getX(), getY(), getWidth(), getHeight(), 2);
        }

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

        mPortrait = Assets.getFor(EResource.PORTRAIT, mPortraitName);
    }

    @Override
    public void move(Deque<Tile> path) {
        mMoveListener.forEach(l -> l.onMoveStarted(this));
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
        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());

        mCurrentAnimation.reset();
        mCurrentAnimation.start();

        mMoveListener.forEach(l -> l.onMoveFinished(this));

    }

    @Override
    public void selected() {
        mIsSelected = true;

        if (!mIsWaiting) {
            mCurrentAnimation = mSelection;

            mCurrentAnimation.setX(getX());
            mCurrentAnimation.setY(getY());
            mCurrentAnimation.reset();
            mCurrentAnimation.start();
        }

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
    public void addMoveFinishedListener(IMoveListener listener) {
        mMoveListener.add(listener);

    }

    @Override
    public void removeMoveFinishedListener(IMoveListener listener) {
        mMoveListener.remove(listener);

    }

    @Override
    public int getMovement() {
        return mMovement;

    }

    @Override
    public int getRange() {
        return mRange;
    }

    @Override
    public void setRange(int range) {
        mRange = range;

    }

    @Override
    public void moveInstantly(int x, int y) {
        this.setX(x);
        this.setY(y);

    }

    @Override
    public void finished() {
        mCurrentAnimation = mHover;
        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());
        mCurrentAnimation.reset();
        mCurrentAnimation.start();

        mIsWaiting = true;

    }

    @Override
    public boolean isWaiting() {
        return mIsWaiting;
    }

    @Override
    public void ready() {
        mIsWaiting = false;

    }

    @Override
    public Image getPortrait() {
        return mPortrait;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getCurrentHP() {
        // TODO Auto-generated method stub
        return 20;
    }

    @Override
    public int getMaximumHP() {
        // TODO Auto-generated method stub
        return 30;
    }
}
