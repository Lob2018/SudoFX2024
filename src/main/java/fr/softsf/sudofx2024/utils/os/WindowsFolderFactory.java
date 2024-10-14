package fr.softsf.sudofx2024.utils.os;

import java.io.File;

import org.springframework.stereotype.Component;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.SUDO_FX_BASE_PATH;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.SUPPOSED_DATA_FOLDER_FOR_SUDO_FX;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for creating and managing Windows-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
@Slf4j
@Component
public class WindowsFolderFactory implements OsDynamicFolders.IOsFoldersFactory {
    private final String LOGS_FOLDER_FOR_SUDO_FX;
    private final String DATA_FOLDER_FOR_SUDO_FX;

    /**
     * Constructor that initializes the data and logs folder paths.
     */
    public WindowsFolderFactory() {
        final String[] folders = createFolders();
        DATA_FOLDER_FOR_SUDO_FX = folders[0];
        LOGS_FOLDER_FOR_SUDO_FX = folders[1];
    }

    /**
     * Creates data and logs folders for the application.
     *
     * @return An array of Strings containing the paths to the data and logs folders.
     */
    public String[] createFolders() {
        boolean bData = isNowCreated(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath());
        boolean bLogs = isNowCreated(SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath());
        if (bData && bLogs) {
            return new String[]{SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath(), SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath()};
        }
        return new String[]{SUDO_FX_BASE_PATH.getPath(), SUDO_FX_BASE_PATH.getPath()};
    }

    /**
     * Attempts to create a folder at the specified path and confirms its creation.
     *
     * @param folderPath The path where the folder should be created.
     * @return true if the folder exists or was successfully created, false otherwise.
     */
    boolean isNowCreated(final String folderPath) {
        try {
            File folder = getFolder(folderPath);
            if (!folder.exists()) {
                return folder.mkdirs();
            }
            return true;
        } catch (SecurityException e) {
            log.error(String.format("██ Security error when creating folder :  %s", e.getMessage()), e);
            return false;
        } catch (Exception e) {
            log.error(String.format("██ Error when creating folder :  %s", e.getMessage()), e);
            return false;
        }
    }

    /**
     * Creates a File object for the given folder path.
     * This method can be overridden for testing purposes.
     *
     * @param folderPath The path of the folder.
     * @return A File object representing the folder.
     */
    File getFolder(final String folderPath) {
        return new File(folderPath);
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