package fr.softsf.sudokufx.common.unit.utils;

import org.junit.jupiter.api.Test;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.*;
import static fr.softsf.sudokufx.utils.MyEnums.ToastLevels.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class MyEnumsUTest {

    @Test
    void testResourcesFxmlPath() {
        assertNotNull(RESOURCES_FXML_PATH.getPath());
        assertNotNull(getClass().getResource(RESOURCES_FXML_PATH.getPath()));
    }

    @Test
    void testResourcesCssPath() {
        assertNotNull(RESOURCES_CSS_PATH.getPath());
        assertNotNull(getClass().getResource(RESOURCES_CSS_PATH.getPath()));
    }

    @Test
    void testI18nPath() {
        assertNotNull(I18N_PATH.getPath());
        assertNotNull(getClass().getResource("/" + I18N_PATH.getPath() + ".properties"));
    }

    @Test
    void testLogoSudoPngPath() {
        assertNotNull(LOGO_SUDO_PNG_PATH.getPath());
        assertNotNull(getClass().getResource(LOGO_SUDO_PNG_PATH.getPath()));
    }

    @Test
    void testUserHome() {
        assertNotNull(USER_HOME.getPath());
    }

    @Test
    void testDataFolder() {
        assertNotNull(DATA_FOLDER.getPath());
    }

    @Test
    void testLogsFolder() {
        assertNotNull(LOGS_FOLDER.getPath());
    }

    @Test
    void testBasePaths() {
        assertNotNull(WINDOWS_BASE_PATH.getPath());
        assertNotNull(LINUX_BASE_PATH.getPath());
        assertNotNull(MACOS_BASE_PATH.getPath());
    }


    @Test
    void testToastLevels() {
        assertNotNull(INFO.getLevel());
        assertNotNull(WARN.getLevel());
        assertNotNull(ERROR.getLevel());
    }

}
