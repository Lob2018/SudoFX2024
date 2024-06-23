package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MyLogbackWindowsITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;

    @Autowired
    MyLogback setupMyLogback;

    @Test
    void testLogs_real_folder_path_is_correct() {
        assertEquals(setupMyLogback.getLogsFolderPath(), osFolderFactory.getOsLogsFolderPath());
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
        assertThrows(RuntimeException.class, () -> {
            setupMyLogback.configureLogback();
        });
    }
}
