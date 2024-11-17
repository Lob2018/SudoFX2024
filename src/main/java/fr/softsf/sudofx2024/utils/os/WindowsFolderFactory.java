package fr.softsf.sudofx2024.utils.os;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for creating and managing WINDOWS OS-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
@Slf4j
public class WindowsFolderFactory implements OsFolderFactoryManager.IOsFolderFactory {
    private final String LOGS_FOLDER_FOR_SUDO_FX;
    private final String DATA_FOLDER_FOR_SUDO_FX;

    /**
     * Constructor that initializes the data and logs folder paths.
     *
     * @param dataFolderPath The intended path for the data folder
     * @param logsFolderPath The intended path for the logs folder
     */
    public WindowsFolderFactory(String dataFolderPath, String logsFolderPath) {
        final String[] folders = OsFolderInitializer.initializeFolders(
                dataFolderPath, logsFolderPath
        );
        DATA_FOLDER_FOR_SUDO_FX = folders[0];
        LOGS_FOLDER_FOR_SUDO_FX = folders[1];
    }

    /**
     * Returns the path to the data folder for the SudoFX application.
     *
     * @return A String representing the path to the data folder.
     */
    @Override
    public String getOsDataFolderPath() {
        return DATA_FOLDER_FOR_SUDO_FX;
    }

    /**
     * Returns the path to the logs folder for the SudoFX application.
     *
     * @return A String representing the path to the logs folder.
     */
    @Override
    public String getOsLogsFolderPath() {
        return LOGS_FOLDER_FOR_SUDO_FX;
    }

}