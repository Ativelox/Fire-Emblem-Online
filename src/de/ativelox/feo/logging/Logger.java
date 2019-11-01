package de.ativelox.feo.logging;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Logger {

    private static ILogger instance = null;

    private Logger() {

    }

    public static ILogger get() {
        if (instance == null) {
            instance = new ConsoleLogger();

        }
        return instance;

    }

}
