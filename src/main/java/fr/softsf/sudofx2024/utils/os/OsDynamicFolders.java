package fr.softsf.sudofx2024.utils.os;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.OsName.WRONG_OS_FOR_TESTS;

@Configuration
public class OsDynamicFolders {

    private String os = OS_NAME.getOs();

    @Autowired
    WindowsFolderFactory windowsFolderFactory;

    /**
     * The OS folder factory
     *
     * @return The OS folder factory
     * @throws IllegalArgumentException The OS is not supported
     */
    @Bean
    public WindowsFolderFactory osFolderFactory() throws IllegalArgumentException {
        if (os == null || !os.contains("windows")) {
            throw new IllegalArgumentException("Windows OS is needed to run SudoFX2024");
        } else {
            return Objects.requireNonNullElseGet(windowsFolderFactory, WindowsFolderFactory::new);
        }
    }

    /**
     * Stubbing setter for wrong os
     */
    void setWrongOsForTests() {
        os = WRONG_OS_FOR_TESTS.getOs();
    }

    /**
     * Stubbing setter for null os
     */
    void setNullOsForTests() {
        os = null;
    }

    public interface IOsFoldersFactory {
        /**
         * Get current OS data folder path
         *
         * @return The data folder path
         */
        String getOsDataFolderPath();

        /**
         * Get current OS logs folder path
         *
         * @return The logs folder path
         */
        String getOsLogsFolderPath();
    }
}