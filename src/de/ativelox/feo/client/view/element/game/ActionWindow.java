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
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
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
public class ActionWindow extends AScreenElement implements IRequireResources, IUpdateable {

    private Image mTop;
    private Image mBottom;

    private final VerticalSelectionManager mSelectionManager;
    private final ConfirmWhenSelectedManager<ACancelableButton> mButtonConfirmManager;

    private final ACancelableButton[] mButtons;

    public ActionWindow(EActionWindowOption... options) {
        super(0, 0, 49 * Display.INTERNAL_RES_FACTOR, (16 * options.length + 5 + 4) * Display.INTERNAL_RES_FACTOR,
                false);

        mButtons = new ACancelableButton[options.length];

        for (int i = 0; i < options.length; i++) {
            mButtons[i] = new ActionWindowButton(0, 0, i, options[i].toString());

        }
        mSelectionManager = new VerticalSelectionManager(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);

        this.load();

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.drawImage(mTop, getX(), getY(), mTop.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mTop.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBottom, getX(), getY() + getHeight() - 5 * Display.INTERNAL_RES_FACTOR,
                mBottom.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mBottom.getHeight(null) * Display.INTERNAL_RES_FACTOR);

        for (final AButtonElement button : mButtons) {
            button.render(g);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        setX(Display.WIDTH - this.getWidth() - 10 * Display.INTERNAL_RES_FACTOR);
        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);

        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;
        }

        mSelectionManager.update(ts);
        mButtonConfirmManager.update(ts);
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

}
