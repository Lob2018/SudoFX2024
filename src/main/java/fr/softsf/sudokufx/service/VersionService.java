package fr.softsf.sudokufx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.utils.MyRegex;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service for managing version information from a GitHub repository.
 */
@Slf4j
@Getter
@Service
public class VersionService {
    private static final String OWNER = "Lob2018";
    private static final String REPO = "SudokuFX";
    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_API_URL = "https://api.github.com/";
    private static final String GITHUB_API_URL_REPO_TAGS = GITHUB_API_URL + "repos/" + OWNER + "/" + REPO + "/tags";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final HttpClient httpClient;
    private final String currentVersion = JVMApplicationProperties.getAppVersion().isEmpty() ? "" : JVMApplicationProperties.getAppVersion().substring(1);
    private String lastVersion = currentVersion;

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
     * Gets the GitHub link to the repository releases page.
     *
     * @return the URL to the repository releases page.
     */
    public String getGitHubLinkToRepositoryReleases() {
        return GITHUB_URL + OWNER + "/" + REPO + "/releases";
    }

    /**
     * Checks if the current version is the latest published package version on GitHub.
     *
     * @return true if the current version is the latest or if there was an error, false otherwise.
     */
    public boolean isLatestGitHubPublishedPackageVersion() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GITHUB_API_URL_REPO_TAGS))
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("██ GitHub API returned non 200 status code: {}", response.statusCode());
                return true;
            }
            List<Map<String, Object>> list = OBJECT_MAPPER.readValue(response.body(), new TypeReference<>() {
            });
            String tagName = list.stream()
                    .findFirst()
                    .map(entry -> (String) entry.get("name"))
                    .map(String::trim)
                    .orElse("");
            if (tagName.length() < 6) {
                log.warn("▓▓ Invalid or too short tag received from GitHub: '{}'", tagName);
                return true;
            }
            lastVersion = tagName.substring(1);
            if (!MyRegex.isValidatedByRegex(lastVersion, MyRegex.getVERSION())) {
                log.warn("▓▓ GitHub version '{}' does not match expected semantic versioning format (X.Y.Z).", lastVersion);
                return true;
            }
            boolean isLatest = this.compareVersions(currentVersion, lastVersion) >= 0;
            log.info("▓▓ Version check: currentVersion={}, lastVersion={}, result={}", currentVersion, lastVersion, isLatest);
            return isLatest;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("██ Interrupted while retrieving GitHub version (currentVersion:{}, lastVersion:{}): {}",
                    currentVersion, Objects.toString(lastVersion, "null"), e.getMessage(), e);
        } catch (Exception e) {
            log.error("██ Exception retrieving GitHub version (currentVersion:{}, lastVersion:{}): {}",
                    currentVersion, Objects.toString(lastVersion, "null"), e.getMessage(), e);
        }
        return true;
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
