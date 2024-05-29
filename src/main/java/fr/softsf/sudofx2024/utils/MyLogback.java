package fr.softsf.sudofx2024.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter2;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.ASCII_LOGO;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
@Configuration
public class MyLogback {
    @Autowired
    WindowsFolderFactory osFolderFactory;

    @Getter
    private String logsFolderPath;
    private static final String LOGS_NAME = "SudokuFX.log";
    private String logBackPath = CONFIG_LOGBACK_PATH.getPath();

    @Bean
    public MyLogback setupMyLogback() {
        logsFolderPath = osFolderFactory.getOsLogsFolderPath();
        System.setProperty("logs", logsFolderPath + "/" + LOGS_NAME);
        LoggerContext context = configureLogback();
        printLogEntryMessage();
        printLogStatus(context);
        return this;
    }

    /**
     * Prints entry message to the log
     */
    private static void printLogEntryMessage() {
        log.info(ASCII_LOGO.getLogBackMessage());
    }

    /**
     * Prints logger status to the log
     *
     * @param context The current logger context
     */
    private static void printLogStatus(final LoggerContext context) {
        StatusPrinter2 statusPrinter2 = new StatusPrinter2();
        statusPrinter2.printInCaseOfErrorsOrWarnings(context);
    }

    /**
     * Configure LogBack
     *
     * @return The Logger context
     */
    LoggerContext configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try (InputStream inputStream = MyLogback.class.getResourceAsStream(logBackPath)) {
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
     * Stubbing setter for logback path
     */
    void setLogBackPathForTests() {
        logBackPath = CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS.getPath();
    }
}