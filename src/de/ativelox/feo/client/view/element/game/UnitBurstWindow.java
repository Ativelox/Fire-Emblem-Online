package de.ativelox.feo.client.view.element.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanSwitchSides;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitBurstWindow extends AScreenElement implements IRequireResources, IUpdateable, ICanSwitchSides {

    private Image mBackground;
    private Image mPortrait;
    private Image mHpImage;

    private float mPortraitRatio;

    private Font mNameFont;

    private IUnit mUnit;

    private boolean mIsHidden;

    public UnitBurstWindow(IUnit unit) {
        super(0, 0, 86 * Display.INTERNAL_RES_FACTOR, 38 * Display.INTERNAL_RES_FACTOR, false);

        this.setUnit(unit);
    }

    public void setUnit(IUnit unit) {
        if (unit == null) {
            return;
        }

        this.load();

        this.setWidth(mBackground.getWidth(null) * Display.INTERNAL_RES_FACTOR);
        this.setHeight(mBackground.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        this.setY(0 + 3 * Display.INTERNAL_RES_FACTOR);

        mNameFont = mNameFont.deriveFont(getWidth() / 10f);

        mPortrait = unit.getPortrait();

        mPortraitRatio = ((float) mPortrait.getHeight(null)) / mPortrait.getWidth(null);

        mUnit = unit;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsHidden) {
            return;
        }

        g.drawImage(mBackground, getX(), getY(), getWidth(), getHeight());
        g.drawImage(mPortrait, getX() - 4 * Display.INTERNAL_RES_FACTOR, getY() - 1 * Display.INTERNAL_RES_FACTOR,
                (int) (5 * getWidth() / 10f), (int) ((5 * getWidth() / 10f) * mPortraitRatio));
        g.drawString(mNameFont, new Color(60, 60, 60), mUnit.getName(), (int) (getX() + getWidth() / 2f),
                (int) (getY() + getHeight() / 2.5f));

        if (mHpImage != null) {
            g.drawImage(mHpImage, (int) (getX() + getWidth() / 2.7f), (int) (getY() + getHeight() / 2f),
                    (int) (mHpImage.getWidth(null) / 1.2f), (int) (mHpImage.getHeight(null) / 1.2f));
        }

    }

    public void show() {
        mIsHidden = false;
    }

    public void hide() {
        mIsHidden = true;
    }

    @Override
    public void load() {
        mBackground = Assets.getFor(EResource.BURST_WINDOW);
        mNameFont = Assets.getFor(EResource.DIALOGUE_FONT);

    }

    @Override
    public void switchTo(ESide side) {
        switch (side) {
        case LEFT:
            setX(2 * Display.INTERNAL_RES_FACTOR);
            break;
        case RIGHT:
            setX(Display.WIDTH - this.getWidth() - 5 * Display.INTERNAL_RES_FACTOR);
            break;
        default:
            break;

        }

    }

    private Image getHpImage() {
        return Assets.getFor(EResource.REGULAR_FONT, "HP" + mUnit.getCurrentHP() + "/" + mUnit.getMaximumHP());
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsHidden) {
            return;
        }
        mHpImage = getHpImage();

    }
}
