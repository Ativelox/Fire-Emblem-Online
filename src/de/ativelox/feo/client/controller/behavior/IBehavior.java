package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IBehavior {

    void setController(GameController controller);

    void onUnitSelect(IUnit unit);

    void onUnitDeselect(IUnit unit);

    void turnStart();

    void turnEnd();

}
