package de.ativelox.feo.client.model.unit;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EDirection;
import de.ativelox.feo.client.model.property.EGender;
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

    private Image mPortrait;

    private final String mName;

    private IMoveRoutine mMover;

    private boolean mIsSelected;

    private final List<ISelectionListener> mSelectionListener;
    private final List<IMoveListener> mMoveListener;

    private final int mMovement;

    private boolean mIsWaiting;

    private EGender mGender;

    private EClass mClass;

    private EAffiliation mAffiliation;

    private final Inventory mInventory;

    private IWeapon mCurrentlyEquipped;

    public DummyUnit(int x, int y, int movement, EGender gender, EClass unitClass, String name,
            EAffiliation affiliation) {
        super(Tile.WIDTH * x, Tile.HEIGHT * y, Tile.WIDTH, Tile.HEIGHT);

        mInventory = new Inventory();

        mName = name;

        mGender = gender;
        mClass = unitClass;

        mAffiliation = affiliation;

        mMoveListener = new ArrayList<>();
        mSelectionListener = new ArrayList<>();
        mMover = new SmoothMoveRoutine(this);

        mMovement = movement;

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
        String suffix = "";
        if (mGender == EGender.MALE) {
            suffix = "_m.png";
        } else {
            suffix = "_f.png";
        }

        String dataName = mClass.toString().toLowerCase() + suffix;

        mHover = Assets.getFor(EResource.MAP_HOVER, dataName);
        mMoveRight = Assets.getFor(EResource.MAP_MOVE_RIGHT, dataName);
        mMoveLeft = Assets.getFor(EResource.MAP_MOVE_LEFT, dataName);
        mMoveUp = Assets.getFor(EResource.MAP_MOVE_UP, dataName);
        mMoveDown = Assets.getFor(EResource.MAP_MOVE_DOWN, dataName);
        mSelection = Assets.getFor(EResource.MAP_SELECTION, dataName);

        mCurrentAnimation = mHover;

        mCurrentAnimation.setX(getX());
        mCurrentAnimation.setY(getY());

        mCurrentAnimation.start();

        if (mAffiliation == EAffiliation.OPPOSED) {
            Palette.convertTo(mHover, Palette.BLUE_RED);
            Palette.convertTo(mMoveRight, Palette.BLUE_RED);
            Palette.convertTo(mMoveLeft, Palette.BLUE_RED);
            Palette.convertTo(mMoveUp, Palette.BLUE_RED);
            Palette.convertTo(mMoveDown, Palette.BLUE_RED);
            Palette.convertTo(mSelection, Palette.BLUE_RED);

        }

        mPortrait = Assets.getFor(EResource.PORTRAIT, mName.toLowerCase() + ".png");
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
        int maxRange = 0;

        for (final IWeapon weapon : getInventory().getWeapons()) {
            if (weapon.getRange() > maxRange) {
                maxRange = weapon.getRange();
            }
        }
        return maxRange;
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

        if (mAffiliation == EAffiliation.ALLIED) {
            Palette.convertTo(mCurrentAnimation, Palette.BLUE_GRAY);
        } else {
            Palette.convertTo(mCurrentAnimation, Palette.RED_GRAY);
        }

        mIsWaiting = true;

    }

    @Override
    public boolean isWaiting() {
        return mIsWaiting;
    }

    @Override
    public void ready() {
        mIsWaiting = false;

        if (mAffiliation == EAffiliation.ALLIED) {
            Palette.convertTo(mCurrentAnimation, Palette.GRAY_BLUE);
        } else {
            Palette.convertTo(mCurrentAnimation, Palette.GRAY_RED);
        }

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

    @Override
    public EAffiliation getAffiliation() {
        return mAffiliation;
    }

    @Override
    public Inventory getInventory() {
        return mInventory;
    }

    @Override
    public int getStr() {
        return 10;
    }

    @Override
    public int getSkill() {
        return 10;
    }

    @Override
    public int getSpd() {
        return 10;
    }

    @Override
    public int getLuck() {
        return 10;
    }

    @Override
    public int getDef() {
        return 10;
    }

    @Override
    public int getRes() {
        return 10;
    }

    @Override
    public Optional<IWeapon> getEquippedWeapon() {
        if (mCurrentlyEquipped == null) {
            return Optional.empty();
        }
        return Optional.of(mCurrentlyEquipped);
    }

    @Override
    public void equip(IWeapon weapon) {
        if (getInventory().getWeapons(w -> w == weapon).size() > 0) {
            mCurrentlyEquipped = weapon;
        }
    }
}
