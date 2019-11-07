package de.ativelox.feo.client.model.unit.item.weapon;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class WeaponFactory {

    private static final Path WEAPON_DATA_PATH = Paths.get("res", "fe6", "weapons", "weapons.txt");

    private static Map<EWeapon, IWeapon> WEAPON_CACHE;

    private WeaponFactory() {

    }

    public static IWeapon generate(EWeapon weapon) {
        if (WEAPON_CACHE == null) {
            init();
        }

        return WEAPON_CACHE.get(weapon);

    }

    public static void init() {
        WEAPON_CACHE = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(WEAPON_DATA_PATH);
            for (final String line : lines) {
                if (line.startsWith("#")) {
                    continue;
                }
                String[] data = line.split("\t+");
                String name = data[0];
                int durability = Integer.parseInt(data[1]);
                int range = Integer.parseInt(data[2]);
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

        } catch (IOException e) {
            Logger.get().logError(e);
        }

    }
}
