package fr.softsf.sudokufx.configuration.os;


/**
 * Interface defining methods to access OS-specific folder paths.
 */
public sealed interface IOsFolderFactory permits LinuxFolderFactory, MacosFolderFactory, WindowsFolderFactory, IMockIOsFolderFactory {
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
