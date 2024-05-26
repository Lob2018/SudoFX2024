package fr.softsf.sudofx2024.utils.os;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WindowsFolderFactoryITest {

//    @Test
//    void testWindowsFolderFactory_whenNotExists_Success() {
//        // GIVEN
//        File fileMock = mock(File.class);
//        // Stubbing mock's methods
//        when(fileMock.exists()).thenReturn(false);
//        when(fileMock.mkdirs()).thenReturn(true);
//        // Spy for WindowsFolderFactory
//        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
//        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
//        // WHEN
//        boolean result = windowsFolderFactory.isNowCreated("testFolder");
//        // THEN
//        verify(fileMock).exists();
//        verify(fileMock).mkdirs();
//        verify(windowsFolderFactory).getFolder(anyString());
//        assertTrue(result);
//    }
//
//    @Test
//    void testWindowsFolderFactory_whenExists_Success() {
//        // GIVEN
//        File fileMock = mock(File.class);
//        // Stubbing mock's methods
//        when(fileMock.exists()).thenReturn(true);
//        when(fileMock.mkdirs()).thenReturn(true);
//        // Spy for WindowsFolderFactory
//        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
//        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
//        // WHEN
//        boolean result = windowsFolderFactory.isNowCreated("testFolder");
//        // THEN
//        verify(fileMock).exists();
//        verify(windowsFolderFactory).getFolder(anyString());
//        assertTrue(result);
//    }
//
//    @Test
//    void testWindowsFolderFactory_throws_SecurityException() {
//        // GIVEN
//        // Mock File object
//        File fileMock = mock(File.class);
//        when(fileMock.exists()).thenReturn(false);
//        when(fileMock.mkdirs()).thenThrow(new SecurityException("Permission denied"));
//        // Create a spy of WindowsFolderFactory
//        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
//        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
//        // WHEN
//        boolean result = windowsFolderFactory.isNowCreated("testFolder");
//        // THEN
//        assertFalse(result);
//        verify(fileMock, times(1)).mkdirs();
//    }
//
//    @Test
//    void testWindowsFolderFactory_throws_Exceptions() {
//        // GIVEN
//        // Mock File object
//        File fileMock = mock(File.class);
//        when(fileMock.exists()).thenReturn(false);
//        when(fileMock.mkdirs()).thenThrow(new RuntimeException(new IOException("IO Error"))); // Simulate any other exception
//        // Create a spy of WindowsFolderFactory
//        WindowsFolderFactory windowsFolderFactory = spy(new WindowsFolderFactory());
//        doReturn(fileMock).when(windowsFolderFactory).getFolder(anyString());
//        // WHEN
//        boolean result = windowsFolderFactory.isNowCreated("testFolder");
//        // THEN
//        assertFalse(result);
//        verify(fileMock, times(1)).mkdirs();
//    }
}

