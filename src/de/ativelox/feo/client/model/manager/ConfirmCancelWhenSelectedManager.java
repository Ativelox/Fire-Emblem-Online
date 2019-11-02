package de.ativelox.feo.client.model.manager;

import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ConfirmCancelWhenSelectedManager<T extends ISelectable & IConfirmable & ICancelable>
        extends ConfirmWhenSelectedManager<T> {

    /**
     * @param elements
     */
    public ConfirmCancelWhenSelectedManager(T[] elements) {
        super(elements);

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (isActiveInitially(EAction.CONFIRMATION)) {
            mElements.stream().filter(e -> e.isSelected()).forEach(e -> e.confirm());

        } else if (isActiveInitially(EAction.CANCEL)) {
            mElements.stream().filter(e -> e.isSelected()).forEach(e -> e.cancel());
        }

        this.cycleFinished();
    }
}
