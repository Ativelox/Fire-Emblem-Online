package de.ativelox.feo.logging;

import de.ativelox.feo.util.Timestamp;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ConsoleLogger implements ILogger {

    private boolean mIsDebug;

    public ConsoleLogger(final boolean isDebug) {
        mIsDebug = isDebug;

    }

    public ConsoleLogger() {
        this(false);
    }

    @Override
    public void log(ELogType type, String message) {
        if (type == ELogType.DEBUG && !mIsDebug) {
            return;
        }
        System.out.println(Timestamp.now() + " [" + type.toString() + "]: " + message);

    }

    @Override
    public void logInfo(String message) {
        log(ELogType.INFO, message);

    }

    @Override
    public void logError(String message, Exception e) {
        log(ELogType.ERROR, message);
        e.printStackTrace();

    }

    @Override
    public void logError(Exception e) {
        log(ELogType.ERROR, "");
        e.printStackTrace();

    }

    @Override
    public void setDebug(boolean isDebug) {
        mIsDebug = isDebug;

    }
}
