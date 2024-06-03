package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.interfaces.IFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileManagerImpl implements IFileManager {
    private static final Logger logger = LogManager.getLogger(FileManagerImpl.class);

    /**
     * Check if folder path exists or create corresponding folder
     *
     * @param folderPath The folder path to check or create
     * @return True if the folder exists, false if not or if an error occurs
     */
    @Override
    public boolean isNowCreated(String folderPath) {
        try {
            File folder = getFile(folderPath);
            if (!folder.exists()) {
                return folder.mkdirs();
            }
            return true;
        } catch (SecurityException e) {
            logger.error("Security error when creating folder : " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.error("Error when creating folder : " + e.getMessage(), e);
            return false;
        }
    }

    public File getFile(String folderPath) {
        return new File(folderPath);
    }
}
