package fr.softsf.sudokufx.configuration.os;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OsFolderInitializerUTest {

    @Test
    void givenNoFileButExistentFolder_whenCreateFolder_thenFolderCreated() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenReturn(true);
        OsFolderInitializer.createFolder(mockFolder);
        verify(mockFolder).exists();
        verify(mockFolder).mkdirs();
    }

    @Test
    void givenNoFileAndNonExistentFolder_whenCreateFolderFails_thenRuntimeExceptionThrown() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            OsFolderInitializer.createFolder(mockFolder);
        });
        assertEquals("Error when creating needed folder: " + mockFolder.getPath(), exception.getMessage());
    }

    @Test
    void givenExistingFolder_whenCreateFolder_thenNoCreationAttempted() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(true);
        OsFolderInitializer.createFolder(mockFolder);
        verify(mockFolder).exists();
        verify(mockFolder, never()).mkdirs();
    }

    @Test
    void givenNonExistentFolder_whenCreateFolder_thenSecurityException() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenThrow(new SecurityException("Security violation"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            OsFolderInitializer.createFolder(mockFolder);
        });
        assertEquals("Security error when creating needed folder: " + mockFolder.getPath(), exception.getMessage());
    }

    @Test
    void givenNonExistentFolder_whenCreateFolder_thenRuntimeException() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenThrow(new RuntimeException("General error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            OsFolderInitializer.createFolder(mockFolder);
        });
        assertEquals("Error when creating needed folder: " + mockFolder.getPath(), exception.getMessage());
    }

}
