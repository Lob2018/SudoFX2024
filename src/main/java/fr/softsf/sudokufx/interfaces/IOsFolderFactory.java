package fr.softsf.sudokufx.interfaces;


/**
 * Interface defining methods to access OS-specific folder paths.
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
