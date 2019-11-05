package de.ativelox.feo.client.view.element.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmCancelWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.IWeapon;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AActionWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class WeaponSelectionWindow extends AActionWindow {

    private IUnit mUnit;

    private Image mTop;
    private Image mBot;

    public WeaponSelectionWindow(IUnit unit) {
        super(EActionWindowType.WEAPON_SELECT, 0, 0, 0, 0, true);

        this.setUnit(unit, new ArrayList<>());

    }

    private void initialize(IWeapon... weapons) {
        setX(10 * Display.INTERNAL_RES_FACTOR);
        setY(12 * Display.INTERNAL_RES_FACTOR);
        setWidth((int) (Display.WIDTH / 2.5f));

        mButtons = new WeaponSelectionButton[weapons.length];

        for (int i = 0; i < weapons.length; i++) {
            mButtons[i] = new WeaponSelectionButton(getX(),
                    4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR), i, weapons[i]);

            mButtons[i].setWidth(getWidth());

        }
        setHeight((16 * weapons.length + 5 + 4) * Display.INTERNAL_RES_FACTOR);

        mSelectionManager = new VerticalSelectionManager<>(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        if (mIsHidden || mButtons.length <= 0) {
            return;
        }

        g.drawImage(mTop, getX(), getY(), getWidth(), mTop.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBot, getX(), getY() + getHeight() - 5 * Display.INTERNAL_RES_FACTOR, getWidth(),
                mBot.getHeight(null) * Display.INTERNAL_RES_FACTOR, 5);

        for (final AButtonElement button : mButtons) {
            button.render(g);
        }
    }

    @Override
    public void update(TimeSnapshot ts) {
        super.update(ts);
        setY(10 * Display.INTERNAL_RES_FACTOR);

        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;
        }

    }

    public void setUnit(IUnit unit, Collection<IWeapon> eligible) {
        if (unit == null) {
            this.initialize();
            return;
        }
        mUnit = unit;
        IWeapon[] eli = new IWeapon[eligible.size()];

        int i = 0;
        for (final IWeapon weapon : eligible) {
            eli[i] = weapon;
            i++;
        }
        this.initialize(eli);
    }

    @Override
    public void switchTo(ESide side) {
        return;

    }

    @Override
    public void load() {
        mTop = Assets.getFor(EResource.ACTION_WINDOW_TOP);
        mBot = Assets.getFor(EResource.ACTION_WINDOW_BOTTOM);

    }
}
