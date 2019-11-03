package de.ativelox.feo.client.view.element.generic;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.manager.ConfirmWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanSwitchSides;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AActionWindow extends AScreenElement implements IRequireResources, IUpdateable, ICanSwitchSides {

    protected VerticalSelectionManager mSelectionManager;
    protected ConfirmWhenSelectedManager<ACancelableButton> mButtonConfirmManager;

    protected ACancelableButton[] mButtons;

    protected boolean mIsHidden;

    private boolean mShowNextTick;

    private EActionWindowType mType;

    public AActionWindow(EActionWindowType type, int x, int y, int width, int height, boolean isPercent) {
        super(x, y, width, height, isPercent);

        mType = type;

        this.load();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsHidden) {
            return;
        }

    }

    @Override
    public abstract void switchTo(ESide side);

    @Override
    public void update(TimeSnapshot ts) {
        if (mShowNextTick) {
            mIsHidden = false;
        } else {
            mIsHidden = true;
        }
        mSelectionManager.update(ts);
        mButtonConfirmManager.update(ts);

    }

    @Override
    public abstract void load();

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

    public EActionWindowType getType() {
        return mType;
    }

}
