package fr.softsf.sudofx2024.utils.os;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
@Component
public class WindowsFolderFactory implements OsDynamicFolders.IOsFoldersFactory {
    private final String LOGS_FOLDER_FOR_SUDO_FX;
    private final String DATA_FOLDER_FOR_SUDO_FX;

    public WindowsFolderFactory() {
        final String[] folders = createFolders();
        DATA_FOLDER_FOR_SUDO_FX = folders[0];
        LOGS_FOLDER_FOR_SUDO_FX = folders[1];
    }

    /**
     * Create data and logs folders
     *
     * @return The array of data and logs folders paths
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
     * Confirm folder creation
     *
     * @param folderPath The folder path
     * @return True for created folder, False otherwise
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
     * Stubbing getter for folder path
     *
     * @param folderPath The folder path
     * @return The file
     */
    File getFolder(final String folderPath) {
        return new File(folderPath);
    }

    @Override
    public String getOsDataFolderPath() {
        return DATA_FOLDER_FOR_SUDO_FX;
    }

    @Override
    public String getOsLogsFolderPath() {
        return LOGS_FOLDER_FOR_SUDO_FX;
    }

}