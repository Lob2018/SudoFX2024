package fr.softsf.sudofx2024.utils.os;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.OsName.WRONG_OS_FOR_TESTS;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

/**
 * Configuration class for managing OS-specific dynamically.
 */
@Configuration
public class OsFolderFactoryManager {

    private String os = OS_NAME.getOs();

    private String windowsIntendedPathDataFolder = WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String windowsIntendedPathLogsFolder = WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathDataFolder = LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathLogsFolder = LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathDataFolder = MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathLogsFolder = MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();

    /**
     * Creates and returns an OS-specific folder factory.
     *
     * @return An implementation of IOsFoldersFactory interface
     * @throws IllegalArgumentException if the OS is not supported
     */
    @Bean
    public IOsFolderFactory osFolderFactory() throws IllegalArgumentException {
        if (os == null || os.isEmpty()) {
            throw new IllegalArgumentException("Operating system is not specified or null.");
        }
        if (os.contains("windows")) {
            return new WindowsFolderFactory(
                    windowsIntendedPathDataFolder,
                    windowsIntendedPathLogsFolder);
        } else if (os.contains("linux")) {
            return new LinuxFolderFactory(
                    linuxIntendedPathDataFolder,
                    linuxIntendedPathLogsFolder);
        } else if (os.contains("mac")) {
            return new MacosFolderFactory(
                    macosIntendedPathDataFolder,
                    macosIntendedPathLogsFolder);
        } else {
            throw new IllegalArgumentException("Unsupported OS: " + os);
        }
    }

    /**
     * Sets the OS to an unsupported value for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setWrongOsForTests() {
        os = WRONG_OS_FOR_TESTS.getOs();
    }

    /**
     * Sets the OS to null for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setNullOsForTests() {
        os = null;
    }

    /**
     * Sets the OS empty for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setEmptyOsForTests() {
        os = "";
    }

    /**
     * Sets Windows OS and set current paths for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setWindowsOsForTests(String windowsIntendedPathDataFolder, String windowsIntendedPathLogsFolder) {
        this.windowsIntendedPathDataFolder = windowsIntendedPathDataFolder;
        this.windowsIntendedPathLogsFolder = windowsIntendedPathLogsFolder;
        os = "windows";
    }

    /**
     * Sets Linux OS and set current paths for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setLinuxOsForTests(String linuxIntendedPathDataFolder, String linuxIntendedPathLogsFolder) {
        this.linuxIntendedPathDataFolder = linuxIntendedPathDataFolder;
        this.linuxIntendedPathLogsFolder = linuxIntendedPathLogsFolder;
        os = "linux";
    }

    /**
     * Sets macOS OS and set current paths for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setMacOSForTests(String macosIntendedPathDataFolder, String macosIntendedPathLogsFolder) {
        this.macosIntendedPathDataFolder = macosIntendedPathDataFolder;
        this.macosIntendedPathLogsFolder = macosIntendedPathLogsFolder;
        os = "mac";
    }

    /**
     * Interface defining methods for accessing OS-specific folder paths.
     */
    public interface IOsFolderFactory {

        /**
         * Retrieves the path to the OS-specific data folder.
         *
         * @return A String representing the path to the data folder
         */
        String getOsDataFolderPath();

        /**
         * Retrieves the path to the OS-specific logs folder.
         *
         * @return A String representing the path to the logs folder
         */
        String getOsLogsFolderPath();
    }
}
