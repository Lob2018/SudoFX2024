package fr.softsf.sudokufx.configuration.os;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * The FolderInitializer class is responsible for creating the necessary folders
 * for the application to function properly. It provides methods to create
 * data and log folders at specified paths, ensuring that these essential
 * directories exist before the application proceeds.
 * This utility class is designed to be used statically and cannot be instantiated.
 */
@Slf4j
public class OsFolderInitializer {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    @ExcludedFromCoverageReportGenerated
    private OsFolderInitializer() {
    }

    /**
     * Creates folders for data and logs. This method attempts to create the specified
     * data and logs folder paths. If either folder cannot be created, it throws a RuntimeException.
     *
     * @param dataFolderPath The path to the data folder to be created.
     * @param logsFolderPath The path to the logs folder to be created.
     * @return An array of strings containing the paths of the created folders.
     */
    static String[] initializeFolders(String dataFolderPath, String logsFolderPath) {
        createFolder(new File(dataFolderPath));
        createFolder(new File(logsFolderPath));
        return new String[]{dataFolderPath, logsFolderPath};
    }

    /**
     * Attempts to create a folder at the specified path and confirms its creation.
     *
     * @param folder The folder that should be created.
     */
    static void createFolder(final File folder) {
        try {
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    log.error("██ Failed to create folder with mkdirs(): {}", folder.getAbsolutePath());
                    throw new RuntimeException("Failed to create folder with mkdirs(): {}" + folder.getAbsolutePath());
                }
                log.info("▓▓ Folder created successfully: {}", folder.getAbsolutePath());
            } else {
                log.info("▓▓ Folder already exists: {}", folder.getAbsolutePath());
            }
        } catch (SecurityException e) {
            log.error("██ Security error when creating needed folder: {}. █ Path: {}", e.getMessage(), folder.getAbsolutePath(), e);
            throw new RuntimeException("Security error when creating needed folder: " + folder.getAbsolutePath(), e);
        } catch (Exception e) {
            log.error("██ Error when creating needed folder: {}. █ Path: {}", e.getMessage(), folder.getAbsolutePath(), e);
            throw new RuntimeException("Error when creating needed folder: " + folder.getAbsolutePath(), e);
        }
    }
}
