package fr.softsf.sudofx2024.unit.utils;

import fr.softsf.sudofx2024.utils.I18n;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class I18nTest {
    private final I18n REF_OBJ_I18N = I18n.getInstance();

    @Test
    @Order(0)
    public void testDefault_language_is_french() {
        // GIVEN
        // WHEN
        String FR = REF_OBJ_I18N.getLanguage();
        // THEN
        assertEquals(FR, "fr");
    }

    @Test
    @Order(1)
    public void testDefault_language_is_french_with_value() {
        // GIVEN
        // WHEN
        String testFR = REF_OBJ_I18N.getValue("test");
        // THEN
        assertEquals(testFR, "testFR");
    }


    @Test
    @Order(2)
    public void testChange_language_to_english_with_value() {
        // GIVEN
        REF_OBJ_I18N.setLocaleBundle("EN");
        // WHEN
        String testEN = REF_OBJ_I18N.getValue("test");
        // THEN
        assertEquals(testEN, "testUS");
    }

    @Test
    @Order(3)
    public void testChanged_language_is_english() {
        // GIVEN
        // WHEN
        String EN = REF_OBJ_I18N.getLanguage();
        // THEN
        assertEquals(EN, "en");
    }

    @Test
    @Order(4)
    public void testChange_english_language_to_french() {
        // GIVEN
        REF_OBJ_I18N.setLocaleBundle("");
        // WHEN
        String testFR = REF_OBJ_I18N.getValue("test");
        // THEN
        assertEquals(testFR, "testFR");
    }

    @Test
    @Order(5)
    public void testChanged_language_is_french() {
        // GIVEN
        // WHEN
        String FR = REF_OBJ_I18N.getLanguage();
        // THEN
        assertEquals(FR, "fr");
    }
}
