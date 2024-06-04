package fr.softsf.sudofx2024.unit.utils;

import org.junit.jupiter.api.Test;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;
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
    void testBasePath() {
        assertNotNull(BASE_PATH.getPath());
    }

}
