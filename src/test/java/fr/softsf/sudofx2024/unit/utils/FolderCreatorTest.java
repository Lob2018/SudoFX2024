package fr.softsf.sudofx2024.unit.utils;

import fr.softsf.sudofx2024.utils.FileManagerImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FolderCreatorTest {

    @Test
    public void testCreateFolder_whenNotExists_Success() {
        // GIVEN
        File fileMock = mock(File.class);
        // Stubbing mock's methods
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenReturn(true);
        // Spy for FileManagerImpl
        FileManagerImpl fileMangerSpy = spy(new FileManagerImpl());
        doReturn(fileMock).when(fileMangerSpy).getFile(anyString());
        // WHEN
        boolean result = fileMangerSpy.isNowCreated("testFolder");
        // THEN
        verify(fileMock).exists();
        verify(fileMock).mkdirs();
        verify(fileMangerSpy).getFile(anyString());
        assertTrue(result);
    }

    @Test
    public void testCreateFolder_whenExists_Success() {
        // GIVEN
        File fileMock = mock(File.class);
        // Stubbing mock's methods
        when(fileMock.exists()).thenReturn(true);
        when(fileMock.mkdirs()).thenReturn(true);
        // Spy for FileManagerImpl
        FileManagerImpl fileMangerSpy = spy(new FileManagerImpl());
        doReturn(fileMock).when(fileMangerSpy).getFile(anyString());
        // WHEN
        boolean result = fileMangerSpy.isNowCreated("testFolder");
        // THEN
        verify(fileMock).exists();
        verify(fileMangerSpy).getFile(anyString());
        assertTrue(result);
    }

    @Test
    public void testCreateFolder_throws_SecurityException() {
        // GIVEN
        // Mock File object
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenThrow(new SecurityException("Permission denied"));
        // Create a spy of FileManagerImpl
        FileManagerImpl fileMangerSpy = spy(new FileManagerImpl());
        doReturn(fileMock).when(fileMangerSpy).getFile(anyString());
        // WHEN
        boolean result = fileMangerSpy.isNowCreated("testFolder");
        // THEN
        assertFalse(result);
        verify(fileMock, times(1)).mkdirs();
    }

    @Test
    public void testCreateFolder_throws_Exceptions() {
        // GIVEN
        // Mock File object
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenThrow(new RuntimeException(new IOException("IO Error"))); // Simulate any other exception
        // Create a spy of FileManagerImpl
        FileManagerImpl fileMangerSpy = spy(new FileManagerImpl());
        doReturn(fileMock).when(fileMangerSpy).getFile(anyString());
        // WHEN
        boolean result = fileMangerSpy.isNowCreated("testFolder");
        // THEN
        assertFalse(result);
        verify(fileMock, times(1)).mkdirs();
    }


}

