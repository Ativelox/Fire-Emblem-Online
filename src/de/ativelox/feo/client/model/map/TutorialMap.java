package de.ativelox.feo.client.model.map;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitFactory;
import de.ativelox.feo.client.model.unit.item.weapon.EWeapon;
import de.ativelox.feo.client.model.unit.item.weapon.ItemFactory;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TutorialMap extends Map {

    public TutorialMap(int x, int y) {
	super("ch0.map", x, y);
	IUnit roy = UnitFactory.get("roy.json", EAffiliation.ALLIED, 1, 0);
	roy.getInventory().add(ItemFactory.generate(EWeapon.RAPIER));
	roy.equip(roy.getInventory().getWeapons()[0]);
	roy.setToCommander();

	IUnit wolt = UnitFactory.get("wolt.json", EAffiliation.ALLIED, 13, 0);
	wolt.getInventory().add(ItemFactory.generate(EWeapon.IRON_BOW));
	wolt.equip(wolt.getInventory().getWeapons()[0]);
	
	IUnit lilina = UnitFactory.get("lilina.json", EAffiliation.ALLIED, 13, 9);
	lilina.getInventory().add(ItemFactory.generate(EWeapon.FIRE));
	lilina.equip(lilina.getInventory().getWeapons()[0]);
	
	

	IUnit soldier1 = UnitFactory.get("soldier.json", EAffiliation.OPPOSED, 9, 5);
	soldier1.getInventory().add(ItemFactory.generate(EWeapon.SLIM_LANCE));
	soldier1.equip(soldier1.getInventory().getWeapons()[0]);

	IUnit soldier2 = UnitFactory.get("soldier.json", EAffiliation.OPPOSED, 19, 5);
	soldier2.getInventory().add(ItemFactory.generate(EWeapon.SLIM_LANCE));
	soldier2.equip(soldier2.getInventory().getWeapons()[0]);

	IUnit soldier3 = UnitFactory.get("soldier.json", EAffiliation.OPPOSED, 17, 7);
	soldier3.getInventory().add(ItemFactory.generate(EWeapon.SLIM_LANCE));
	soldier3.equip(soldier3.getInventory().getWeapons()[0]);

	IUnit archer = UnitFactory.get("archer.json", EAffiliation.OPPOSED, 18, 6);
	archer.getInventory().add(ItemFactory.generate(EWeapon.IRON_BOW));
	archer.equip(archer.getInventory().getWeapons()[0]);

	IUnit bors = UnitFactory.get("bors.json", EAffiliation.OPPOSED, 22, 4);
	bors.getInventory().add(ItemFactory.generate(EWeapon.IRON_LANCE));
	bors.equip(bors.getInventory().getWeapons()[0]);
	bors.setToCommander();

	this.add(roy);
	this.add(soldier1);
	this.add(soldier2);
	this.add(soldier3);
	this.add(bors);
	this.add(archer);
	this.add(wolt);
	this.add(lilina);

    }
}
