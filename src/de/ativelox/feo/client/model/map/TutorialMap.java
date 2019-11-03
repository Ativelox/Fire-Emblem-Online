package de.ativelox.feo.client.model.map;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.client.model.unit.UnitFactory;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TutorialMap extends Map {

    public TutorialMap(int x, int y) {
        super("ch0.map", x, y);
        this.add(UnitFactory.get(EUnit.FIR, EAffiliation.ALLIED, 0, 0));
        this.add(UnitFactory.get(EUnit.ROY, EAffiliation.ALLIED, 5, 5));
        this.add(UnitFactory.get(EUnit.FIR, EAffiliation.OPPOSED, 10, 5));

    }
}
