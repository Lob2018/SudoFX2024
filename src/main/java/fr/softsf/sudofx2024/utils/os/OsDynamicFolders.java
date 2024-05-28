package fr.softsf.sudofx2024.utils.os;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.WRONG_OS_FOR_TESTS;
import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;

@Configuration
public class OsDynamicFolders {

    private String os = OS_NAME.getOs();

    @Autowired
    WindowsFolderFactory windowsFolderFactory;

    @Bean
    public WindowsFolderFactory osFolderFactory() throws IllegalArgumentException {
        if (os != null && os.contains("win")) {
            return windowsFolderFactory;
        } else {
            throw new IllegalArgumentException("Windows OS is needed to run SudoFX2024");
        }
    }
    // Only for tests
    void setWrongOsForTests() {
        os = WRONG_OS_FOR_TESTS.getOs();
    }
    // Only for tests
    void setNullOsForTests() {
        os = null;
    }

    public interface IOsFoldersFactory {
        String getOsDataFolderPath();

        String getOsLogsFolderPath();
    }
}