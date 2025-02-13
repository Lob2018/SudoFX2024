package fr.softsf.sudokufx.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SystemStubsExtension.class)
class VersionServiceTest {
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static final String GITHUB_LINK_TO_REPOSITORY_RELEASES = "https://github.com/Lob2018/SudokuFX/releases";
    private static final String JSON = """
            [
              {
                "name": "%s",
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v1.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v1.0.0",
                "commit": {
                  "sha": "e59cb3b14bd859250910d51985f50dabb89ee788",
                  "url": "https://api.github.com/repos/Lob2018/SudokuFX/commits/e59cb3b14bd859250910d51985f50dabb89ee788"
                },
                "node_id": "REF_kwDOLH8vKbByZWZzL3RhZ3MvdjEuMC4w"
              },
              {
                "name": "v0.9.8",
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v1.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v1.0.0",
                "commit": {
                  "sha": "e59cb3b14bd859250910d51985f50dabb89ee788",
                  "url": "https://api.github.com/repos/Lob2018/SudokuFX/commits/e59cb3b14bd859250910d51985f50dabb89ee788"
                },
                "node_id": "REF_kwDOLH8vKbByZWZzL3RhZ3MvdjEuMC4w"
              }
            ]
            """;
    private AutoCloseable closeable;
    private ListAppender<ILoggingEvent> logWatcher;

    @SystemStub
    private SystemProperties systemProperties;
    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockResponse;

    @BeforeEach
    void eachSetup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(VersionService.class)).addAppender(logWatcher);
        systemProperties.set(APP_VERSION_PROPERTY, "1.1.1");
        closeable = MockitoAnnotations.openMocks(this);
        Mockito.reset(mockHttpClient, mockResponse);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(VersionService.class)).detachAndStopAllAppenders();
    }

    @Test
    void releasesLink_success() {
        VersionService versionService = new VersionService(mockHttpClient);
        assertEquals(GITHUB_LINK_TO_REPOSITORY_RELEASES,
                versionService.getGitHubLinkToRepositoryReleases(),
                "The GitHub link to repository releases must be " + GITHUB_LINK_TO_REPOSITORY_RELEASES);
    }

    @Test
    void testIsLatestGitHubPublishedPackageVersion_emptyResult_true() throws Exception {
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockResponse.body()).thenReturn("[]");
        Mockito.when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        VersionService versionService = new VersionService(mockHttpClient);
        boolean isLatestVersion = versionService.isLatestGitHubPublishedPackageVersion().join();
        assertTrue(isLatestVersion);
        Mockito.verify(mockHttpClient, Mockito.times(1))
                .sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 403, 404, 500})
    void testIsLatestGitHubPublishedPackageVersion_wrongHttpStatusCodes_true(int httpStatusCode) throws Exception {
        Mockito.when(mockResponse.statusCode()).thenReturn(httpStatusCode);
        Mockito.when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        VersionService versionService = new VersionService(mockHttpClient);
        boolean isLatestVersion = versionService.isLatestGitHubPublishedPackageVersion().join();
        assertTrue(isLatestVersion);
        Mockito.verify(mockHttpClient, Mockito.times(1))
                .sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        assertTrue(logWatcher.list.getFirst().getFormattedMessage().contains("██ GitHub API returned non 200 status code: " + httpStatusCode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"v1.1.1", "v0.0.0", "v0.1.1", "v1.0.1", "v1.1.0", "v2.1.1", "v1.2.1", "v1.1.2", "v99.99.99"})
    void testIsLatestGitHubPublishedPackageVersion_onlineEmptyOldestSameAndNewerVersions(String onLineVersion) throws Exception {
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        String jsonResponse = String.format(JSON, onLineVersion);
        Mockito.when(mockResponse.body()).thenReturn(jsonResponse);
        Mockito.when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        VersionService versionService = new VersionService(mockHttpClient);
        boolean isLatestVersion = versionService.isLatestGitHubPublishedPackageVersion().join();
        if (onLineVersion.equals("v2.1.1") || onLineVersion.equals("v1.2.1") || onLineVersion.equals("v1.1.2") || onLineVersion.equals("v99.99.99")) {
            assertFalse(isLatestVersion);
        } else {
            assertTrue(isLatestVersion);
        }
        Mockito.verify(mockHttpClient, Mockito.times(1))
                .sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "vv.v.v"})
    void testIsLatestGitHubPublishedPackageVersion_onlineInvalidVersions(String onLineVersion) throws Exception {
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        String jsonResponse = String.format(JSON, onLineVersion);
        Mockito.when(mockResponse.body()).thenReturn(jsonResponse);
        Mockito.when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        VersionService versionService = new VersionService(mockHttpClient);
        boolean isLatestVersion = versionService.isLatestGitHubPublishedPackageVersion().join();
        assertTrue(isLatestVersion);
        Mockito.verify(mockHttpClient, Mockito.times(1))
                .sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        String message = logWatcher.list.getFirst().getFormattedMessage();
        switch (onLineVersion) {
            case "vv.v.v" -> {
                assertTrue(message.contains("▓▓ GitHub version 'v.v.v' does not match expected semantic versioning format (X.Y.Z)."));
            }
            case "" -> {
                assertTrue(message.contains("▓▓ Invalid or too short tag received from GitHub: ''"));
            }
            default -> throw new AssertionError("Unexpected version format: " + onLineVersion);
        }
    }
}






