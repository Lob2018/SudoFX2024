package fr.softsf.sudofx2024.unit.utils;

import fr.softsf.sudofx2024.utils.I18n;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class I18nUTest {

    @Test
    @Order(0)
    void testDefault_language_is_french() {
        // GIVEN
        // WHEN
        String FR = I18n.getLanguage();
        // THEN
        assertEquals("fr", FR);
    }

    @Test
    @Order(1)
    void testDefault_language_is_french_with_value() {
        // GIVEN
        // WHEN
        String testFR = I18n.getValue("test");
        // THEN
        assertEquals("testFR", testFR);
    }


    @Test
    @Order(2)
    void testChange_language_to_english_with_value() {
        // GIVEN
        I18n.setLocaleBundle("EN");
        // WHEN
        String testEN = I18n.getValue("test");
        // THEN
        assertEquals("testUS", testEN);
    }

    @Test
    @Order(3)
    void testChanged_language_is_english() {
        // GIVEN
        // WHEN
        String EN = I18n.getLanguage();
        // THEN
        assertEquals("en", EN);
    }

    @Test
    @Order(4)
    void testChange_english_language_to_french() {
        // GIVEN
        I18n.setLocaleBundle("");
        // WHEN
        String testFR = I18n.getValue("test");
        // THEN
        assertEquals("testFR", testFR);
    }

    @Test
    @Order(5)
    void testChanged_language_is_french() {
        // GIVEN
        // WHEN
        String FR = I18n.getLanguage();
        // THEN
        assertEquals("fr", FR);
    }
}
