package de.ativelox.feo.client.model.manager;

import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ConfirmWhenSelectedManager<T extends ISelectable & IConfirmable> extends InputReceiver
        implements IUpdateable {

    private final List<T> mElements;

    public ConfirmWhenSelectedManager(T[] elements) {
        mElements = new ArrayList<>();

        for (final T c : elements) {
            mElements.add(c);
        }
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (isActiveInitially(EAction.CONFIRMATION)) {
            mElements.stream().filter(e -> e.isSelected()).forEach(e -> e.confirm());

        }
        this.cycleFinished();
    }

    public void clear() {
        mElements.clear();

    }

}
