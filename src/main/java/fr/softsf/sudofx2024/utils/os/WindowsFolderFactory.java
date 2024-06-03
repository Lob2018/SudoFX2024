package fr.softsf.sudofx2024.utils.os;

import fr.softsf.sudofx2024.interfaces.IFileManager;
import fr.softsf.sudofx2024.interfaces.IOsFoldersFactory;

public final class WindowsFolderFactory implements IOsFoldersFactory {
    private final String SUDO_FX_BASE_PATH = OsInfo.USER_HOME.replace("\\","/") + "/AppData/Local/Soft64.fr/SudokuFX";
    private final String LOGS_FOLDER_FOR_SUDO_FX = SUDO_FX_BASE_PATH + "/logs-sudofx";
    private final String SUPPOSED_DATA_FOLDER_FOR_SUDO_FX = SUDO_FX_BASE_PATH + "/data-sudofx";
    private final String DATA_FOLDER_FOR_SUDO_FX;
    private final IFileManager iFileManager;

    public String createDataFolder() {
        if (iFileManager != null) {
            return iFileManager.isNowCreated(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX) ?
                    SUPPOSED_DATA_FOLDER_FOR_SUDO_FX :
                    SUDO_FX_BASE_PATH;
        } else {
            return SUDO_FX_BASE_PATH;
        }
    }

    public WindowsFolderFactory(IFileManager iFileManagerP) {
        iFileManager = iFileManagerP;
        DATA_FOLDER_FOR_SUDO_FX = createDataFolder();
    }

    /**
     * Get data folder path
     *
     * @return The data folder path
     */
    @Override
    public String getOsDataFolderPath() {
        return DATA_FOLDER_FOR_SUDO_FX;
    }

    /**
     * Get logs folder path
     *
     * @return The logs folder path
     */
    @Override
    public String getOsLogsFolderPath() {
        return LOGS_FOLDER_FOR_SUDO_FX;
    }

}
