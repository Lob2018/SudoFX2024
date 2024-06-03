package fr.softsf.sudofx2024.unit.utils;

import fr.softsf.sudofx2024.utils.Log;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.OsInfo;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class LogTest {

    private final OsDynamicFolders osDynamicFolders =new OsDynamicFolders(OsInfo.OS_NAME);
    private final Log REF_OBJ_LOG = new Log(this.osDynamicFolders.getIOsFoldersFactory());

    @Test
    public void testLogs_real_folder_path_is_correct() {
        // GIVEN WHEN THEN
        assertEquals(REF_OBJ_LOG.getLogsFolderPath(), osDynamicFolders.getIOsFoldersFactory().getOsLogsFolderPath());
    }

    @Test
    public void testLogs_real_folder_exists() {
        // GIVEN WHEN THEN
        Path dossier = Path.of(REF_OBJ_LOG.getLogsFolderPath());
        assertTrue(Files.exists(dossier));
    }

    @Test
    public void testLog_as_fatal(@Mock Logger logger) {
        // GIVEN WHEN
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        logger.fatal("This is a critical message");
        // THEN
        verify(logger, times(1)).fatal(stringCaptor.capture());
        assertEquals("This is a critical message", stringCaptor.getValue());
    }
}
