package de.ativelox.feo.client;

import de.ativelox.feo.client.controller.MainController;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Launcher {

    public static void main(String[] args) {
        MainController mc = new MainController();
        mc.initialize();
        mc.run();

    }

}
