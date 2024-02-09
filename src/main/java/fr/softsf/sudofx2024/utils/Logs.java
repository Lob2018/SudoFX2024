package fr.softsf.sudofx2024.utils;

import org.apache.logging.log4j.core.config.Configurator;

import java.util.Objects;

public class Logs {
    private static final Logs instance = new Logs();
    private String currentLogsPath = "";

    /**
     * Private constructor cannot be instantiated outside of Logs class
     * Log4j2 initialization
     */
    private Logs() {
        this.currentLogsPath = this.setCurrentDefaultLogsPathInALog4j2SystemProperty();
        this.setCurrentLog4j2ConfigurationFilePath();
    }

    /**
     * Get the unique instance of the Logs class
     *
     * @return The unique Logs class instance
     */
    public static Logs getInstance() {
        return instance;
    }

    /**
     * Get the current logs path
     *
     * @return the current logs path
     */
    public final String getCurrentLogsPath() {
        return currentLogsPath;
    }

    /**
     * Set the default logs' path inside a log4j2 system property
     *
     * @return The current default logs path stored as Log4j2 system property
     */
    private String setCurrentDefaultLogsPathInALog4j2SystemProperty() {
        String currentUserDirectoryProperty = "user.dir";
        String defaultLogsPathForWindows = "ProgramData\\SudokuFX";
        String pathForLogs = System.getProperty(currentUserDirectoryProperty).substring(0, 3) + defaultLogsPathForWindows;
        String log4j2LogsSystemPropertyName = "logs";
        System.setProperty(log4j2LogsSystemPropertyName, pathForLogs);
        return pathForLogs;
    }

    /**
     * Set the current Log4j2 configuration file's path
     */
    private void setCurrentLog4j2ConfigurationFilePath() {
        String pathToLog4j2Configuration = "/fr/softsf/sudofx2024/config/log4j2.xml";
        String log4j2Path = Objects.requireNonNull(this.getClass().getResource(pathToLog4j2Configuration).toExternalForm());
        Configurator.initialize(null, log4j2Path);
    }

}