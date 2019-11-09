package de.ativelox.feo.client.model.map;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitFactory;
import de.ativelox.feo.client.model.unit.item.weapon.EWeapon;
import de.ativelox.feo.client.model.unit.item.weapon.WeaponFactory;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TutorialMap extends Map {

    public TutorialMap(int x, int y) {
        super("ch0.map", x, y);
        IUnit roy = UnitFactory.get("roy.json", EAffiliation.ALLIED, 1, 0);
        roy.getInventory().add(WeaponFactory.generate(EWeapon.RAPIER));
        roy.equip(roy.getInventory().getWeapons()[0]);
        roy.setToCommander();
        
        IUnit evilRoy = UnitFactory.get("roy.json", EAffiliation.OPPOSED, 1, 1);
        evilRoy.getInventory().add(WeaponFactory.generate(EWeapon.RAPIER));
        evilRoy.equip(evilRoy.getInventory().getWeapons()[0]);
        evilRoy.setToCommander();

        IUnit evilFir = UnitFactory.get("fir.json", EAffiliation.OPPOSED, 10, 4);
        evilFir.getInventory().add(WeaponFactory.generate(EWeapon.WO_DAO));
        evilFir.equip(evilFir.getInventory().getWeapons()[0]);

        IUnit chad = UnitFactory.get("chad.json", EAffiliation.OPPOSED, 5, 3);
        chad.getInventory().add(WeaponFactory.generate(EWeapon.IRON_SWORD));
        chad.equip(chad.getInventory().getWeapons()[0]);

        IUnit fir = UnitFactory.get("fir.json", EAffiliation.ALLIED, 0, 2);
        fir.getInventory().add(WeaponFactory.generate(EWeapon.WO_DAO));
        fir.equip(fir.getInventory().getWeapons()[0]);

        this.add(roy);
        this.add(evilRoy);
        this.add(evilFir);
        this.add(chad);
        this.add(fir);

    }
}
