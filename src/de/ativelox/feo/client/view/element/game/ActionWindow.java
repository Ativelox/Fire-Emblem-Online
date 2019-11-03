package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmCancelWhenSelectedManager;
import de.ativelox.feo.client.model.manager.ConfirmWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanSwitchSides;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.element.generic.ACancelableButton;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ActionWindow extends AScreenElement implements IRequireResources, IUpdateable, ICanSwitchSides {

    private Image mTop;
    private Image mBottom;

    private VerticalSelectionManager mSelectionManager;
    private ConfirmWhenSelectedManager<ACancelableButton> mButtonConfirmManager;

    private ACancelableButton[] mButtons;

    private boolean mIsHidden;

    private boolean mShowNextTick;

    public ActionWindow(EActionWindowOption... options) {
        super(0, 0, 49 * Display.INTERNAL_RES_FACTOR, (16 * options.length + 5 + 4) * Display.INTERNAL_RES_FACTOR,
                false);

        this.load();

        this.setActions(options);

    }

    public void setActions(EActionWindowOption... options) {
        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);

        mButtons = new ACancelableButton[options.length];

        for (int i = 0; i < options.length; i++) {
            mButtons[i] = new ActionWindowButton(getX(),
                    4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR), i,
                    options[i].toString());

        }
        setHeight((16 * options.length + 5 + 4) * Display.INTERNAL_RES_FACTOR);

        mSelectionManager = new VerticalSelectionManager(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsHidden) {
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
        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);
        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;
        }

        mSelectionManager.update(ts);
        mButtonConfirmManager.update(ts);

        if (mShowNextTick) {
            mIsHidden = false;

        } else {
            mIsHidden = true;

        }
    }

    @Override
    public void load() {
        mTop = Assets.getFor(EResource.ACTION_WINDOW_TOP);
        mBottom = Assets.getFor(EResource.ACTION_WINDOW_BOTTOM);

    }

    public void registerTo(InputManager im) {
        im.register((IMovementListener) mSelectionManager);
        im.register((IActionListener) mButtonConfirmManager);

    }

    public void addCancelListener(ICancelListener listener) {
        for (final ACancelableButton button : mButtons) {
            button.addCancelListener(listener);

        }
    }

    public void addConfirmListener(IConfirmListener listener) {
        for (final ACancelableButton button : mButtons) {
            button.add(listener);

        }
    }

    public void show() {
        mSelectionManager.unblock();
        mButtonConfirmManager.unblock();
        mShowNextTick = true;
    }

    public void hide() {
        mSelectionManager.block();
        mButtonConfirmManager.block();
        mShowNextTick = false;
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
