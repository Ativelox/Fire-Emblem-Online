package de.ativelox.feo.logging;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ILogger {
    
    void setDebug(boolean isDebug);

    void log(ELogType type, String message);

    void logInfo(String message);

    void logError(String message, Exception e);

    void logError(Exception e);

}
