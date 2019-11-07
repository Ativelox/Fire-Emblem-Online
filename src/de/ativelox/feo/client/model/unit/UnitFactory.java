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

    public static IUnit get(EUnit unit, EClass unitClass, EAffiliation affiliation, int x, int y) {

        switch (unit) {
        case FIR:
            return new UnitBuilder().setUnit(unit).setAffiliation(affiliation).setCoordinates(x, y)
                    .setGender(EGender.FEMALE).setName("Fir").setClass(unitClass)
                    .setStats(19, 6, 9, 10, 3, 3, 1, 5).setStatGrowth(0.75, 0.25, 0.5, 0.55, 0.50, 0.15, 0.20).build();
        case ROY:
            return new UnitBuilder().setUnit(unit).setAffiliation(affiliation).setCoordinates(x, y)
                    .setGender(EGender.MALE).setName("Roy").setClass(unitClass).setStats(18, 5, 5, 7, 7, 5, 0, 5)
                    .setStatGrowth(0.8, 0.4, 0.5, 0.4, 0.6, 0.25, 0.3).build();
        case CHAD:
            return new UnitBuilder().setUnit(unit).setAffiliation(affiliation).setCoordinates(x, y)
                    .setGender(EGender.MALE).setName("Chad").setClass(unitClass).setStats(16, 3, 3, 10, 4, 2, 0, 6)
                    .setStatGrowth(0.85, 0.5, 0.5, 0.8, 0.6, 0.25, 0.15).build();
        default:
            Logger.get().log(ELogType.ERROR, "Unknown/Unsupported unit: " + unit);
            break;
        }
        return null;

    }
}
