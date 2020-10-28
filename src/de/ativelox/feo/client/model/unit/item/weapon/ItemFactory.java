package de.ativelox.feo.client.model.unit.item.weapon;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.EItem;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.Item;
import de.ativelox.feo.client.model.util.Range;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ItemFactory {

    private static final Path WEAPON_DATA_PATH = Paths.get("res", "fe6", "items", "weapons.txt");
    private static final Path ITEM_DATA_PATH = Paths.get("res", "fe6", "items", "items.txt");

    private static Map<EWeapon, IWeapon> WEAPON_CACHE;
    private static Map<EItem, IItem> ITEM_CACHE;

    private ItemFactory() {

    }

    public static IWeapon generate(EWeapon weapon) {
	if (WEAPON_CACHE == null) {
	    init();
	}

	return (IWeapon) WEAPON_CACHE.get(weapon).copy();

    }

    public static IItem generate(EItem item) {
	if (ITEM_CACHE == null) {
	    init();
	}
	return ITEM_CACHE.get(item).copy();

    }

    public static IItem generate(EItem item, BiConsumer<IUnit, IItem> use) {
	if (ITEM_CACHE == null) {
	    init();
	}
	IItem itemCopy = ITEM_CACHE.get(item).copy();
	itemCopy.overwriteUsage(use);

	return itemCopy;

    }

    public static IWeapon generate(EWeapon weapon, BiConsumer<IUnit, IItem> use) {
	if (WEAPON_CACHE == null) {
	    init();
	}
	IWeapon weaponCopy = (IWeapon) WEAPON_CACHE.get(weapon).copy();
	weaponCopy.overwriteUsage(use);

	return weaponCopy;

    }

    public static void init() {
	WEAPON_CACHE = new HashMap<>();
	ITEM_CACHE = new HashMap<>();

	try {
	    List<String> lines = Files.readAllLines(WEAPON_DATA_PATH);
	    for (final String line : lines) {
		if (line.startsWith("#")) {
		    continue;
		}
		String[] data = line.split("\t+");
		String name = data[0];
		int durability = Integer.parseInt(data[1]);

		Range<Integer> range;

		String[] rangedata = data[2].split("-");
		if (rangedata.length < 2) {
		    range = Range.ofSingular(Integer.parseInt(rangedata[0]));

		} else {
		    range = Range.of(Integer.parseInt(rangedata[0]), Integer.parseInt(rangedata[1]));

		}

		int crit = Integer.parseInt(data[3]);
		int might = Integer.parseInt(data[4]);
		int accuracy = Integer.parseInt(data[5]);
		int weight = Integer.parseInt(data[6]);
		EDamageType type = EDamageType.valueOf(data[7]);
		EWeapon weapon = EWeapon.valueOf(data[8]);
		String fileName = data[9];

		Image image = Assets.getFor(EResource.WEAPON_IMAGE, fileName);

		WEAPON_CACHE.put(weapon,
			new Weapon(name, durability, range, crit, might, accuracy, weight, type, image));

	    }

	    lines = Files.readAllLines(ITEM_DATA_PATH);
	    for (final String line : lines) {
		if (line.startsWith("#")) {
		    continue;
		}
		String[] data = line.split("\t+");
		String name = data[0];
		String fileName = data[1];
		EItem item = EItem.valueOf(data[2]);

		Image image = Assets.getFor(EResource.ITEM_IMAGE, fileName);

		ITEM_CACHE.put(item, new Item(name, image));

	    }

	} catch (IOException e) {
	    Logger.get().logError(e);
	}

    }
}
