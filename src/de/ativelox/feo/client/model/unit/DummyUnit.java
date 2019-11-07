package de.ativelox.feo.client.model.unit;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
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
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.callback.IMoveListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.unit.item.Inventory;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.model.util.IMoveRoutine;
import de.ativelox.feo.client.model.util.SmoothMoveRoutine;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

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

    private boolean mIsWaiting;

    private EGender mGender;

    private EClass mClass;

    private EAffiliation mAffiliation;

    private final Inventory mInventory;

    private IWeapon mCurrentlyEquipped;

    private EUnit mUnit;

    private int mCurrentHp;

    private int mMov;
    private int mHp;
    private int mStr;
    private int mSkl;
    private int mSpd;
    private int mLck;
    private int mDef;
    private int mRes;
    private double mGrowthHp;
    private double mGrowthStr;
    private double mGrowthSkl;
    private double mGrowthSpd;
    private double mGrowthLck;
    private double mGrowthDef;
    private double mGrowthRes;

    public DummyUnit(int x, int y, EUnit unit, EGender gender, EClass unitClass, String name, EAffiliation affiliation,
            int mov, int hp, int str, int skl, int spd, int lck, int def, int res, double growthHp, double growthStr,
            double growthSkl, double growthSpd, double growthLck, double growthDef, double growthRes) {
        super(Tile.WIDTH * x, Tile.HEIGHT * y, Tile.WIDTH, Tile.HEIGHT);

        mMov = mov;
        mCurrentHp = hp;
        mHp = hp;
        mStr = str;
        mSkl = skl;
        mSpd = spd;
        mLck = lck;
        mDef = def;
        mRes = res;
        mGrowthHp = growthHp;
        mGrowthStr = growthStr;
        mGrowthSkl = growthSkl;
        mGrowthSpd = growthSpd;
        mGrowthLck = growthLck;
        mGrowthDef = growthDef;
        mGrowthRes = growthRes;

        mInventory = new Inventory();

        mUnit = unit;

        mName = name;

        mGender = gender;
        mClass = unitClass;

        mAffiliation = affiliation;

        mMoveListener = new ArrayList<>();
        mSelectionListener = new ArrayList<>();
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
    public void move(Iterator<EdgeCost<Tile, Edge<Tile>>> path, boolean reversed) {
        mMoveListener.forEach(l -> l.onMoveStarted(this));
        mMover.move(path, reversed, getMov());

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
    public int getMov() {
        return mMov;

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
        return mCurrentHp;
    }

    @Override
    public int getMaximumHP() {
        return mHp;
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
        return mStr;
    }

    @Override
    public int getSkill() {
        return mSkl;
    }

    @Override
    public int getSpd() {
        return mSpd;
    }

    @Override
    public int getLuck() {
        return mLck;
    }

    @Override
    public int getDef() {
        return mDef;
    }

    @Override
    public int getRes() {
        return mRes;
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

    @Override
    public EGender getGender() {
        return mGender;
    }

    @Override
    public EClass getCurrentClass() {
        return mClass;
    }

    @Override
    public EUnit getUnit() {
        return mUnit;
    }

    @Override
    public void removeHp(int hp) {
        mCurrentHp -= hp;

    }
}
