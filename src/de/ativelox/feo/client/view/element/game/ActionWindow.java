package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmCancelWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AActionWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.element.generic.ACancelableButton;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ActionWindow extends AActionWindow {

    private Image mTop;
    private Image mBottom;

    public ActionWindow(EActionWindowOption... options) {
        super(EActionWindowType.DEFAULT, 0, 0, 49 * Display.INTERNAL_RES_FACTOR,
                (16 * options.length + 5 + 4) * Display.INTERNAL_RES_FACTOR, false);

        this.setActions(options);

    }

    public void setActions(EActionWindowOption... options) {
        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);

        mButtons = new ACancelableButton[options.length];

        for (int i = 0; i < options.length; i++) {
            mButtons[i] = new ActionWindowButton(getX(),
                    4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR), i,
                    options[i].toString(), getType());

        }
        setHeight((16 * options.length + 5 + 4) * Display.INTERNAL_RES_FACTOR);

        mSelectionManager = new VerticalSelectionManager(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        if (mIsHidden || mButtons.length <= 0) {
            return;
        }
        g.drawImage(mTop, getX(), getY(), mTop.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mTop.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBottom, getX(), getY() + getHeight() - 5 * Display.INTERNAL_RES_FACTOR,
                mBottom.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mBottom.getHeight(null) * Display.INTERNAL_RES_FACTOR, 5);

        for (final AButtonElement button : mButtons) {
            button.render(g);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        super.update(ts);

        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);
        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;
        }
    }

    @Override
    public void load() {
        mTop = Assets.getFor(EResource.ACTION_WINDOW_TOP);
        mBottom = Assets.getFor(EResource.ACTION_WINDOW_BOTTOM);

    }

    @Override
    public void switchTo(ESide side) {
        switch (side) {
        case LEFT:
            setX(12 * Display.INTERNAL_RES_FACTOR);
            break;
        case RIGHT:
            setX(Display.WIDTH - this.getWidth() - 10 * Display.INTERNAL_RES_FACTOR);
            break;
        default:
            break;

        }

    }
}
