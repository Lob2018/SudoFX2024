package fr.softsf.sudokufx.configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter2;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

import static fr.softsf.sudokufx.enums.LogBackTxt.ASCII_LOGO;
import static fr.softsf.sudokufx.enums.LogBackTxt.OPTIMIZING;
import static fr.softsf.sudokufx.enums.Paths.*;


/**
 * Configuration class for Logback logging framework. This class sets up and
 * configures Logback for the application.
 */
@Slf4j
@Configuration
public class MyLogbackConfig {

    @Getter
    private final String logsFolderPath;
    private String logBackPath = CONFIG_LOGBACK_PATH.getPath();

    /**
     * Constructor that initializes the logging configuration.
     *
     * @param iOsFolderFactory Factory for creating OS-specific folder paths.
     */
    public MyLogbackConfig(IOsFolderFactory iOsFolderFactory) {
        logsFolderPath = iOsFolderFactory.getOsLogsFolderPath();
        System.setProperty("logs", logsFolderPath + "/" + LOGS_FILE_NAME.getPath());
        LoggerContext context = configureLogback();
        printLogStatus(context);
    }

    /**
     * Prints the logger status to the log in case of errors or warnings.
     *
     * @param context The current logger context
     */
    private void printLogStatus(final LoggerContext context) {
        StatusPrinter2 statusPrinter2 = new StatusPrinter2();
        statusPrinter2.printInCaseOfErrorsOrWarnings(context);
    }

    /**
     * Prints the application's ASCII logo to the log as an entry message.
     * If the Spring context is set to exit on refresh,
     * it also logs an optimizing startup message.
     */
    public void printLogEntryMessage() {
        log.info(ASCII_LOGO.getLogBackMessage());
        if (JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh())
            log.info(OPTIMIZING.getLogBackMessage());
    }

    /**
     * Configures Logback using the specified configuration file.
     *
     * @return The configured LoggerContext
     * @throws RuntimeException if there's an error during configuration
     */
    LoggerContext configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try (InputStream inputStream = MyLogbackConfig.class.getResourceAsStream(logBackPath)) {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(inputStream);
        } catch (JoranException | IOException e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    /**
     * Sets an invalid logback configuration path for testing purposes. This
     * method should only be used in test scenarios.
     */
    void setLogBackPathForTests() {
        logBackPath = CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS.getPath();
    }
}
