package fr.softsf.sudokufx.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import fr.softsf.sudokufx.utils.os.OsFolderFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static fr.softsf.sudokufx.utils.MyEnums.LogBackTxt.OPTIMIZING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MyLogbackITest {

    private final IOsFolderFactory currentIOsFolderFactory;
    private ListAppender<ILoggingEvent> logWatcher;
    private final MyLogback setupMyLogback;

    @Autowired
    public MyLogbackITest(MyLogback setupMyLogback) {
        this.setupMyLogback = setupMyLogback;
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        currentIOsFolderFactory = osFolderFactoryManager.osFolderFactory();
    }

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MyLogback.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MyLogback.class)).detachAndStopAllAppenders();
    }

    @Test
    void testLogs_real_folder_path_is_correct() {
        assertEquals(setupMyLogback.getLogsFolderPath(), currentIOsFolderFactory.getOsLogsFolderPath());
    }

    @Test
    void testLogs_real_folder_exists() {
        Path dossier = Path.of(setupMyLogback.getLogsFolderPath());
        assertTrue(Files.exists(dossier));
    }

    @Test
    void testLog_as_fatal(@Mock Logger logger) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        logger.error("This is a critical message");
        verify(logger, times(1)).error(stringCaptor.capture());
        assertEquals("This is a critical message", stringCaptor.getValue());
    }

    @Test
    void testConfigureLogbackWithInvalidPath() {
        setupMyLogback.setLogBackPathForTests();
        assertThrows(RuntimeException.class, setupMyLogback::configureLogback);
    }

    @Test
    void testLogEntryMessageWithSpringContextIsOnRefresh() {
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        setupMyLogback.printLogEntryMessage();
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assert (logWatcher.list.getLast().getFormattedMessage()).contains(OPTIMIZING.getLogBackMessage());
    }


}


