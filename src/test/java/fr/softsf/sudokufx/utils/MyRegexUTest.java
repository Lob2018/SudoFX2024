package fr.softsf.sudokufx.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MyRegexUTest {

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(MyRegex.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(MyRegex.class)).detachAndStopAllAppenders();
    }

    @Test
    void givenNullText_whenValidateByRegex_thenFalseAndErrorLogged() {
        boolean value = MyRegex.INSTANCE.isValidatedByRegex(null, MyRegex.INSTANCE.getAlphanumericPattern());
        assertFalse(value, "Text is null then it returns false");
        assert (logWatcher.list.getFirst().getFormattedMessage()).contains("██ Exception caught inside isValidatedByRegex, text is null, pattern is");
    }

    @Test
    void givenNullPattern_whenValidateByRegex_thenFalseAndErrorLogged() {
        boolean value = MyRegex.INSTANCE.isValidatedByRegex("Test", null);
        assertFalse(value, "Pattern is null then it returns false");
        assert (logWatcher.list.getFirst().getFormattedMessage()).contains("██ Exception caught inside isValidatedByRegex, text is Test, pattern is null");
    }


}
