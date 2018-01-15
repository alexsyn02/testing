package ua.com.testing.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Receives exception and logs it according to logger configuration
     *
     * @param exception as a instance of {@code Exception} class or its subclass
     */
    public static void exceptionHandler(Exception exception) {
        LOGGER.error(exception);
    }

    /**
     * Receives event message and logs it according to logger configuration
     *
     * @param eventMessage some message about event
     */
    public static void eventHandler(String eventMessage) {
        LOGGER.info(eventMessage);
    }
}
