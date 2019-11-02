package de.ativelox.feo.client.view.element.generic;

import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.callback.ICancelListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class ACancelableButton extends AButtonElement implements ICancelable {

    private final List<ICancelListener> mCancelListener;

    public ACancelableButton(int x, int y, int width, int height, boolean isPercent, EResource background, int order,
            String text) {
        super(x, y, width, height, isPercent, background, order, text);

        mCancelListener = new ArrayList<>();
    }

    public ACancelableButton(int x, int y, int width, int height, boolean isPercent, EResource background, int order) {
        this(x, y, width, height, isPercent, background, order, "");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void cancel() {
        mCancelListener.forEach(l -> l.onCancel(this));

    }

    @Override
    public void addCancelListener(ICancelListener listener) {
        mCancelListener.add(listener);

    }

    @Override
    public void removeCancelListener(ICancelListener listener) {
        mCancelListener.remove(listener);

    }

    @Override
    protected abstract void renderSelection(DepthBufferedGraphics g);

}
