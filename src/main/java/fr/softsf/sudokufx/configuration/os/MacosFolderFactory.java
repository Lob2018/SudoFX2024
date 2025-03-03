package fr.softsf.sudokufx.configuration.os;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for creating and managing MACOS OS-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
@Slf4j
final class MacosFolderFactory implements IOsFolderFactory {
    private final String logsFolderForSudoFx;
    private final String dataFolderForSudoFx;

    /**
     * Constructor that initializes the data and logs folder paths.
     *
     * @param dataFolderPath The intended path for the data folder
     * @param logsFolderPath The intended path for the logs folder
     */
    public MacosFolderFactory(String dataFolderPath, String logsFolderPath) {
        final String[] folders = OsFolderInitializer.INSTANCE.initializeFolders(
                dataFolderPath, logsFolderPath
        );
        dataFolderForSudoFx = folders[0];
        logsFolderForSudoFx = folders[1];
    }

    @Override
    public String getOsDataFolderPath() {
        return dataFolderForSudoFx;
    }

    @Override
    public String getOsLogsFolderPath() {
        return logsFolderForSudoFx;
    }

}
