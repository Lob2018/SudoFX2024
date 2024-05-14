package fr.softsf.sudofx2024.utils.os;

import fr.softsf.sudofx2024.utils.MyEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
public final class WindowsFolderFactory implements OsDynamicFolders.IOsFoldersFactory {
    private final String LOGS_FOLDER_FOR_SUDO_FX;
    private final String DATA_FOLDER_FOR_SUDO_FX;

    public WindowsFolderFactory() {
        final String[] folders = createFolders();
        DATA_FOLDER_FOR_SUDO_FX = folders[0];
        LOGS_FOLDER_FOR_SUDO_FX = folders[1];
    }

    public String[] createFolders() {
        boolean bData = isNowCreated(SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath());
        boolean bLogs = isNowCreated(SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath());
        if (bData && bLogs) {
            return new String[]{SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath(), SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath()};
        }
        return new String[]{SUDO_FX_BASE_PATH.getPath(), SUDO_FX_BASE_PATH.getPath()};
    }

    boolean isNowCreated(String folderPath) {
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

    File getFolder(String folderPath) {
        return new File(folderPath);
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
