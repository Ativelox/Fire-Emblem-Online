package de.ativelox.feo.client.model.util;

import java.awt.event.MouseEvent;

import de.ativelox.feo.client.model.property.EClickType;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MouseButtonMapper {

    private MouseButtonMapper() {

    }

    public static EClickType getType(MouseEvent e) {
        switch (e.getButton()) {
        case MouseEvent.BUTTON1:
            return EClickType.LEFT;
        case MouseEvent.BUTTON3:
            return EClickType.RIGHT;
        default:
            Logger.get().log(ELogType.ERROR, "Couldn't parse the given mouse button: " + e.getButton());
            break;
        }
        return EClickType.EITHER;
    }

}
