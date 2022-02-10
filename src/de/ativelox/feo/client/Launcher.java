package de.ativelox.feo.client;

import de.ativelox.feo.client.controller.MainController;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Launcher {

    public static void main(String[] args) {
//	SpriteSheet.generateFrom(Paths.get("res", "dumps", "fe6", "ranged_attack", "mage_f"),
//		Paths.get("res", "fe6", "ranged_attack", "mage_f.png"), 240, 160);
//	SpriteSheet.generateFrom(Paths.get("res", "dumps", "fe6", "ranged_crit", "mage_f"),
//		Paths.get("res", "fe6", "ranged_crit", "mage_f.png"), 240, 160);
	MainController mc = new MainController();
	mc.initialize();
	mc.run();
    }
}
