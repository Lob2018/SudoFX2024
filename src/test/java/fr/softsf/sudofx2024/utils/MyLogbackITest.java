package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
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
class MyLogbackITest {

    @Autowired
    WindowsFolderFactory osFolderFactory;

    private MyLogback REF_OBJ_MyLogback;

    @BeforeEach
    void setup() {
        REF_OBJ_MyLogback = new MyLogback(osFolderFactory);
    }

    @Test
    void testLogs_real_folder_path_is_correct() {
        // GIVEN WHEN THEN
        assertEquals(REF_OBJ_MyLogback.getLogsFolderPath(), osFolderFactory.getOsLogsFolderPath());
    }

    @Test
    void testLogs_real_folder_exists() {
        // GIVEN WHEN THEN
        Path dossier = Path.of(REF_OBJ_MyLogback.getLogsFolderPath());
        assertTrue(Files.exists(dossier));
    }

    @Test
    void testLog_as_fatal(@Mock Logger logger) {
        // GIVEN WHEN
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        logger.error("This is a critical message");
        // THEN
        verify(logger, times(1)).error(stringCaptor.capture());
        assertEquals("This is a critical message", stringCaptor.getValue());
    }

    @Test
    void testConfigureLogbackWithInvalidPath() {
        REF_OBJ_MyLogback.setLogBackPathForTests();
        assertThrows(RuntimeException.class, () -> REF_OBJ_MyLogback.configureLogback());
    }
}
