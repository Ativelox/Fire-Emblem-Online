package de.ativelox.feo.client.model.map;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.client.model.unit.DummyWeapon;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitFactory;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TutorialMap extends Map {

    public TutorialMap(int x, int y) {
        super("ch0.map", x, y);
        IUnit roy = UnitFactory.get(EUnit.ROY, EAffiliation.ALLIED, 0, 0);
        roy.getInventory().add(new DummyWeapon("1 Range", 1));
        roy.getInventory().add(new DummyWeapon("2 Range", 2));

        IUnit evilRoy = UnitFactory.get(EUnit.ROY, EAffiliation.OPPOSED, 8, 5);
        evilRoy.getInventory().add(new DummyWeapon("Durandel", 2));
        evilRoy.equip(evilRoy.getInventory().getWeapons()[0]);

        IUnit evilFir = UnitFactory.get(EUnit.FIR, EAffiliation.OPPOSED, 1, 0);

        evilRoy.getInventory().add(new DummyWeapon("1 Range", 1));

//        this.add(UnitFactory.get(EUnit.FIR, EAffiliation.ALLIED, 0, 0));
        this.add(roy);

        this.add(evilRoy);
        this.add(evilFir);

    }
}
