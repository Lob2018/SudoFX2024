package fr.softsf.sudofx2024.utils.os;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WindowsFolderFactoryITest {

    @Test
    void testWindowsFolderFactory_whenNotExists_Success() {
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenReturn(true);
        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
        boolean result = windowsFolderFactory.isNowCreated("testFolder");
        verify(fileMock).exists();
        verify(fileMock).mkdirs();
        verify(windowsFolderFactory).getFolder(anyString());
        assertTrue(result);
    }

    @Test
    void testWindowsFolderFactory_whenExists_Success() {
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(true);
        when(fileMock.mkdirs()).thenReturn(true);
        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
        boolean result = windowsFolderFactory.isNowCreated("testFolder");
        verify(fileMock).exists();
        verify(windowsFolderFactory).getFolder(anyString());
        assertTrue(result);
    }

    @Test
    void testWindowsFolderFactory_throws_SecurityException() {
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenThrow(new SecurityException("Permission denied"));
        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
        boolean result = windowsFolderFactory.isNowCreated("testFolder");
        assertFalse(result);
        verify(fileMock, times(1)).mkdirs();
    }

    @Test
    void testWindowsFolderFactory_throws_Exceptions() {
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);
        when(fileMock.mkdirs()).thenThrow(new RuntimeException(new IOException("IO Error")));
        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
        boolean result = windowsFolderFactory.isNowCreated("testFolder");
        assertFalse(result);
        verify(fileMock, times(1)).mkdirs();
    }
}

