package de.ativelox.feo.client;

import de.ativelox.feo.client.controller.MainController;
import de.ativelox.feo.client.model.util.Range;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Launcher {

    public static void main(String[] args) {
//	SpriteSheet.generateFrom(Paths.get("res", "dumps", "fe6", "ranged_attack", "archer_m"),
//		Paths.get("res", "fe6", "ranged_attack", "archer_m.png"), 240, 160);
//	SpriteSheet.generateFrom(Paths.get("res", "dumps", "fe6", "ranged_crit", "archer_m"),
//		Paths.get("res", "fe6", "ranged_crit", "archer_m.png"), 240, 160);
	MainController mc = new MainController();
	mc.initialize();
	mc.run();
    }
}
