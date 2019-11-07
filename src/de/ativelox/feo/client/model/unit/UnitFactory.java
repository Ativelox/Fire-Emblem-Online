package de.ativelox.feo.client.model.unit;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitFactory {

    private UnitFactory() {

    }

    public static IUnit get(EUnit unit, EAffiliation affiliation, int x, int y) {

        switch (unit) {
        case FIR:
            return new DummyUnit(x, y, 5, unit, EGender.FEMALE, EClass.SWORDMASTER, "Fir", affiliation);
        case ROY:
            return new DummyUnit(x, y, 5, unit, EGender.MALE, EClass.LORD_ROY, "Roy", affiliation);
        default:
            Logger.get().log(ELogType.ERROR, "Unknown/Unsupported unit: " + unit);
            break;
        }
        return null;

    }
}
