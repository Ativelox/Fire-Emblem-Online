package de.ativelox.feo.client.model.property.routine;

import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.client.model.sound.IProduceSound;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ConfirmableRoutine implements IConfirmable {

    private final List<IConfirmListener> mConfirmListener;

    private final IConfirmable mCaller;

    public ConfirmableRoutine(IConfirmable caller) {
        mConfirmListener = new ArrayList<>();
        mCaller = caller;

    }

    @Override
    public void add(IConfirmListener listener) {
        mConfirmListener.add(listener);

    }

    @Override
    public void remove(IConfirmListener listener) {
        mConfirmListener.remove(listener);

    }

    @Override
    public void confirm() {
        mConfirmListener.forEach(c -> c.onConfirm(mCaller));

    }
}
