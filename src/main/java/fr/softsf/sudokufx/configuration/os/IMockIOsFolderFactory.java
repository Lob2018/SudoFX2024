package fr.softsf.sudokufx.configuration.os;

/**
 * Mock interface for testing, extending the sealed {@code IOsFolderFactory}.
 * <p>Declared {@code non-sealed} to allow Mockito mocking, bypassing the restrictions of sealed interfaces.</p>
 * <p><strong>Test Usage:</strong></p>
 * <pre>{@code
 * IMockIOsFolderFactory mockFactory = mock(IMockIOsFolderFactory.class);
 * when(mockFactory.getOsDataFolderPath()).thenReturn("mocked/path");
 * }</pre>
 *
 * @see IOsFolderFactory
 */
public non-sealed interface IMockIOsFolderFactory extends IOsFolderFactory {
}

