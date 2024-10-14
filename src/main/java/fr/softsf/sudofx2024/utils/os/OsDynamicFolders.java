package fr.softsf.sudofx2024.utils.os;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.OsName.WRONG_OS_FOR_TESTS;

/**
 * Configuration class for managing OS-specific dynamic folders. Currently
 * supports Windows OS only.
 */
@Configuration
public class OsDynamicFolders {

    private String os = OS_NAME.getOs();

    @Autowired
    WindowsFolderFactory windowsFolderFactory;

    /**
     * Creates and returns an OS-specific folder factory.
     *
     * @return An implementation of IOsFoldersFactory interface
     * @throws IllegalArgumentException if the OS is not supported (currently
     * only Windows is supported)
     */
    @Bean
    public IOsFoldersFactory osFolderFactory() throws IllegalArgumentException {
        if (os == null || !os.contains("windows")) {
            throw new IllegalArgumentException("Windows OS is needed to run SudoFX2024");
        } else {
            return Objects.requireNonNullElseGet(windowsFolderFactory, WindowsFolderFactory::new);
        }
    }

    /**
     * Sets the OS to an unsupported value for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setWrongOsForTests() {
        os = WRONG_OS_FOR_TESTS.getOs();
    }

    /**
     * Sets the OS to null for testing purposes.
     * This method should only be used in test scenarios.
     */
    void setNullOsForTests() {
        os = null;
    }

    /**
     * Interface defining methods for accessing OS-specific folder paths.
     */
    public interface IOsFoldersFactory {

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
}
