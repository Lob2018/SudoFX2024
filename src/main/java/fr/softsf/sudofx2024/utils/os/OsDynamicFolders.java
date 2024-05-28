package fr.softsf.sudofx2024.utils.os;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.softsf.sudofx2024.utils.MyEnums.OsName.OS_NAME;

@Configuration
public class OsDynamicFolders {

    @Autowired
    WindowsFolderFactory windowsFolderFactory;

    @Bean
    public WindowsFolderFactory osFolderFactory() throws IllegalArgumentException {
        String os = OS_NAME.getOs();
        if (os != null && os.contains("win")) {
            return windowsFolderFactory;
        } else {
            throw new IllegalArgumentException("Windows OS is needed to run SudoFX");
        }
    }

    public interface IOsFoldersFactory {
        String getOsDataFolderPath();

        String getOsLogsFolderPath();
    }
}