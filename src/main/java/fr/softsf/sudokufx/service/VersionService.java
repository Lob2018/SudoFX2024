package fr.softsf.sudokufx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.dto.github.TagDto;
import fr.softsf.sudokufx.utils.MyRegex;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Service for managing version information from a GitHub repository.
 */
@Slf4j
@org.springframework.stereotype.Service
public class VersionService extends Service<Boolean> {

    private static final String OWNER = "Lob2018";
    private static final String REPO = "SudokuFX";
    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_API_URL = "https://api.github.com/";
    private static final String GITHUB_API_URL_REPO_TAGS = GITHUB_API_URL + "repos/" + OWNER + "/" + REPO + "/tags";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final HttpClient httpClient;
    private final String currentVersion = JVMApplicationProperties.getAppVersion().isEmpty() ? "" : JVMApplicationProperties.getAppVersion().substring(1);
    private final SimpleBooleanProperty versionUpToDate = new SimpleBooleanProperty(true);

    /**
     * Constructs a VersionService with the provided HttpClient.
     *
     * @param httpClient the HttpClient to use for HTTP requests.
     */
    @Autowired
    public VersionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new task to check if the latest version of the software is up to date.
     * The task checks the latest version and returns the result as a Boolean.
     *
     * @return A Task that performs the version check and returns true if up to date, false otherwise.
     */
    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                checkLatestVersion();
                return versionUpToDate.get();
            }
        };
    }

    /**
     * Provides a read-only property that indicates whether the software version is up to date.
     * This property can be used to bind UI elements or other components to the version status.
     *
     * @return A read-only Boolean property representing the version's up-to-date status.
     */
    public ReadOnlyBooleanProperty versionUpToDateProperty() {
        return versionUpToDate;
    }

    /**
     * Gets the GitHub link to the repository releases page.
     *
     * @return the URL to the repository releases page.
     */
    public String getGitHubLinkToRepositoryReleases() {
        return GITHUB_URL + OWNER + "/" + REPO + "/releases";
    }

    /**
     * Checks the latest version of the software by querying the GitHub API for the repository's tags.
     * The method sends an asynchronous HTTP request to the GitHub API, processes the response,
     * and updates the version status in the `versionUpToDate` property.
     * <p>
     * The boolean value returned by this method is primarily used for testing purposes, and in production,
     * the version status is directly updated in the `versionUpToDate` property using `versionUpToDate.set(result)`.
     *
     * @return A CompletableFuture that returns a boolean value indicating whether the current version
     * is up to date (true if up to date, false if an update is available). This return value is
     * used exclusively for testing purposes and is not part of the production flow.
     */
    public CompletableFuture<Boolean> checkLatestVersion() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GITHUB_API_URL_REPO_TAGS))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .orTimeout(5, TimeUnit.SECONDS)
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            log.error("██ GitHub API returned non 200 status code: {}", response.statusCode());
                            return true; // En cas d'erreur, on considère la version actuelle comme à jour
                        }
                        return parseResponse(response.body());
                    })
                    .exceptionally(ex -> {
                        if (ex.getCause() instanceof InterruptedException) {
                            log.warn("▓▓ GitHub version check was interrupted", ex);
                            Thread.currentThread().interrupt();
                        } else if (ex.getCause() instanceof TimeoutException) {
                            log.warn("▓▓ Timeout while checking GitHub version");
                        } else {
                            log.error("██ Exception retrieving GitHub version: {}", ex.getMessage(), ex);
                        }
                        return true;
                    })
                    .thenApply(result -> {
                        Platform.runLater(() -> versionUpToDate.set(result));
                        return result;
                    });
        } catch (Exception ex) {
            log.error("██ Exception retrieving GitHub version: {}", ex.getMessage(), ex);
            return CompletableFuture.completedFuture(true);
        }
    }

    /**
     * Parses the JSON response from the GitHub API to extract the latest published version.
     * <p>
     * The method retrieves the latest tag name from the response, validates its format,
     * and compares it with the current application version.
     *
     * @param json The raw JSON response from the GitHub API.
     * @return true if the current version is up to date or if an error occurs, false if an update is available.
     */
    private boolean parseResponse(String json) {
        try {
            List<TagDto> list = OBJECT_MAPPER.readValue(json, new TypeReference<>() {
            });
            String tagName = list.stream()
                    .findFirst()
                    .map(TagDto::name)
                    .map(String::trim)
                    .orElse("");
            if (tagName.length() < 6) {
                log.warn("▓▓ Invalid or too short tag received from GitHub: '{}'", tagName);
                return true;
            }
            String lastVersion = tagName.substring(1);
            if (!MyRegex.isValidatedByRegex(lastVersion, MyRegex.getVERSION())) {
                log.warn("▓▓ GitHub version '{}' does not match expected semantic versioning format (X.Y.Z).", lastVersion);
                return true;
            }
            boolean isLatest = compareVersions(currentVersion, lastVersion) >= 0;
            log.info("▓▓ Version check: currentVersion={}, lastVersion={}, result={}", currentVersion, lastVersion, isLatest);
            return isLatest;
        } catch (Exception e) {
            log.error("██ Error parsing GitHub API response: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * Compares two version strings in the format MAJOR.MINOR.PATCH.
     *
     * @param version1 the first version string (e.g., "1.2.3").
     * @param version2 the second version string (e.g., "1.3.0").
     * @return a negative integer if version1 is older,
     * a positive integer if version1 is newer,
     * or 0 if both versions are equal.
     * @throws NumberFormatException if the version strings are not properly formatted.
     */
    private int compareVersions(final String version1, final String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int majorComparison = Integer.compare(Integer.parseInt(v1[0]), Integer.parseInt(v2[0]));
        if (majorComparison != 0) {
            return majorComparison;
        }
        int minorComparison = Integer.compare(Integer.parseInt(v1[1]), Integer.parseInt(v2[1]));
        if (minorComparison != 0) {
            return minorComparison;
        }
        return Integer.compare(Integer.parseInt(v1[2]), Integer.parseInt(v2[2]));
    }
}
