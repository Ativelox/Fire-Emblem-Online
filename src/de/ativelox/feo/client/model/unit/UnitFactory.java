package de.ativelox.feo.client.model.unit;

import java.util.Optional;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitFactory {

    private UnitFactory() {

    }

    public static IUnit get(String fileName, EAffiliation affiliation, int x, int y) {
        Optional<UnitProperties> prop = UnitProperties.load(fileName);

        if (prop.isEmpty()) {
            Logger.get().log(ELogType.ERROR, "Couldn't find a unit file associated with: " + fileName);
        }
        return new DummyUnit(x, y, affiliation, prop.get());

    }
}
