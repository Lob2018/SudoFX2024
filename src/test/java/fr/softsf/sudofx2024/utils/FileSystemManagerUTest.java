package fr.softsf.sudofx2024.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.spi.ILoggingEvent;

class FileSystemManagerUTest {
//    private Path path1;
//    private final String suffix = "suffix";
//
//    private final FileSystemManager fileSystemManager = new FileSystemManager();
//
//    private ListAppender<ILoggingEvent> logWatcher;
//
//    @BeforeEach
//    void setup() {
//        logWatcher = new ListAppender<>();
//        logWatcher.start();
//        ((Logger) LoggerFactory.getLogger(FileSystemManager.class)).addAppender(logWatcher);
//    }
//
//    @AfterEach
//    void tearDown() {
//        ((Logger) LoggerFactory.getLogger(FileSystemManager.class)).detachAndStopAllAppenders();
//    }
//
//    @TempDir
//    Path tempDir;
//
//    @BeforeEach
//    public void setUp() {
//        try {
//            Files.createDirectories(tempDir.resolve("testFolder/" + suffix + "/toto.txt"));
//            path1 = tempDir.resolve("testFolder/" + suffix + "/toto.txt");
//        } catch (InvalidPathException | IOException ipe) {
//            System.err.println("error creating temporary test file in " + this.getClass().getSimpleName());
//        }
//    }
//
//    @Test
//    void testDeleteFolder_success() throws Exception {
//        // GIVEN WHEN
//        boolean result = fileSystemManager.deleteFolder(path1.getParent(), suffix);
//        // THEN
//        assertTrue(result);
//    }
//
//    @Test
//    void testDeleteFolderIncorrectDirectoryPath_fail() throws Exception {
//        // GIVEN WHEN
//        boolean result = fileSystemManager.deleteFolder(path1.getParent(), suffix + "/toto.txt");
//        // THEN
//        assertFalse(result);
//    }
//
//    @Test
//    void testFilesWalkHandleNullPointerException_fail() {
//        try (MockedStatic<Files> mocked = Mockito.mockStatic(Files.class)) {
//            mocked.when(() -> Files.walk(path1.getParent())).thenThrow(new NullPointerException("Test NullPointerException"));
//            // WHEN
//            fileSystemManager.deleteFolder(path1.getParent(), suffix);
//            // THEN
//            assert (logWatcher.list.get(logWatcher.list.size() - 1).getFormattedMessage()).contains("Test NullPointerException");
//        }
//    }
//
//    @Test
//    void testDeleteFile_fail() {
//        Path mockPath = mock(Path.class);
//        Throwable returnedThrowable = fileSystemManager.deleteFile(mockPath);
//        assertInstanceOf(NullPointerException.class, returnedThrowable);
//    }

}
