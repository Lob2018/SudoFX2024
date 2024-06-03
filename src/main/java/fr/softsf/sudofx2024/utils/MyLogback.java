package fr.softsf.sudofx2024.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter2;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.ASCII_LOGO;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
public final class MyLogback {
    @Getter
    private final String logsFolderPath;
    private static final String LOGS_NAME = "SudokuFX.log";
    private String logBackPath = CONFIG_LOGBACK_PATH.getPath();

    public MyLogback(final OsDynamicFolders.IOsFoldersFactory iosFolders) {
        this.logsFolderPath = iosFolders.getOsLogsFolderPath();
        System.setProperty("logs", logsFolderPath + "/" + LOGS_NAME);
        LoggerContext context = configureLogback();
        printLogEntryMessage();
        printLogStatus(context);
    }

    private static void printLogEntryMessage() {
        log.info(ASCII_LOGO.getLogBackMessage());
    }

    private static void printLogStatus(final LoggerContext context) {
        StatusPrinter2 statusPrinter2 = new StatusPrinter2();
        statusPrinter2.printInCaseOfErrorsOrWarnings(context);
    }

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

    void setLogBackPathForTests() {
        logBackPath = CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS.getPath();
    }
}