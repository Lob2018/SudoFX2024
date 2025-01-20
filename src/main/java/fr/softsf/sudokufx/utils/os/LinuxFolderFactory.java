package fr.softsf.sudokufx.utils.os;

import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for creating and managing LINUX OS-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
@Slf4j
public class LinuxFolderFactory implements IOsFolderFactory {
    private final String logsFolderForSudoFx;
    private final String dataFolderForSudoFx;


    /**
     * Constructor that initializes the data and logs folder paths.
     *
     * @param dataFolderPath The intended path for the data folder
     * @param logsFolderPath The intended path for the logs folder
     */
    public LinuxFolderFactory(String dataFolderPath, String logsFolderPath) {
        final String[] folders = OsFolderInitializer.initializeFolders(
                dataFolderPath, logsFolderPath
        );
        dataFolderForSudoFx = folders[0];
        logsFolderForSudoFx = folders[1];
    }

    /**
     * Returns the path to the data folder for the SudoFX application.
     *
     * @return A String representing the path to the data folder.
     */
    @Override
    public String getOsDataFolderPath() {
        return dataFolderForSudoFx;
    }

    /**
     * Returns the path to the logs folder for the SudoFX application.
     *
     * @return A String representing the path to the logs folder.
     */
    @Override
    public String getOsLogsFolderPath() {
        return logsFolderForSudoFx;
    }

}
