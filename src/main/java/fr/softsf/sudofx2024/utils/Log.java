package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.interfaces.IOsFoldersFactory;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Objects;

public class Log {
    private final String logsFolderPath;

    /**
     * Constructor of Log class
     * Log4j2 initialization
     */
    public Log(IOsFoldersFactory iosFolders) {
        this.logsFolderPath = iosFolders.getOsLogsFolderPath();
        setCurrentDefaultLogsPathInALog4j2SystemProperty();
        setCurrentLog4j2ConfigurationFilePath();
    }

    /**
     * Set the default logs' path inside a log4j2 system property
     */
    private void setCurrentDefaultLogsPathInALog4j2SystemProperty() {
        System.setProperty("logs", logsFolderPath);
    }

    /**
     * Set the current Log4j2 configuration file's path
     */
    private void setCurrentLog4j2ConfigurationFilePath() {
        String log4j2ConfigurationPath = Objects.requireNonNull(this.getClass().getResource("/fr/softsf/sudofx2024/config/log4j2.xml")).toExternalForm();
        Configurator.initialize(null, log4j2ConfigurationPath);
    }

    /**
     * Get the logs folder path
     *
     * @return The logs folder path
     */
    public String getLogsFolderPath() {
        return logsFolderPath;
    }

}