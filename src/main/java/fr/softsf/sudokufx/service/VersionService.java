package fr.softsf.sudokufx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
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
    private final HttpClient httpClient;
    private final String currentVersion = getFormattedVersionNumberOnly(JVMApplicationProperties.getAppVersion());
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
        ObjectMapper objectMapper = new ObjectMapper();
        boolean result = true;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GITHUB_API_URL_REPO_TAGS))
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("██ The status code retrieving the last GitHub published package version isn't 200 : {}", response.statusCode());
                return true;
            }
            List<Map<String, Object>> list = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            if (!list.isEmpty()) {
                lastVersion = getFormattedVersionNumberOnly((String) list.getFirst().get("name"));
            }
            result = this.compareVersions(currentVersion, lastVersion) >= 0;
            log.info("▓▓ The result retrieving the last GitHub published package version is : currentVersion={}, lastVersion={}, result={})", currentVersion, lastVersion, result);
        } catch (Exception e) {
            log.error("██ Exception retrieving the last GitHub published package version (currentVersion:{}, lastVersion:{}, result:{}) : {}", currentVersion, lastVersion, result, e.getMessage(), e);
        }
        return result;
    }

    /**
     * Extracts the version number from a string.
     *
     * @param version the version string.
     * @return the extracted version number.
     */
    private String getFormattedVersionNumberOnly(final String version) {
        return version.replaceAll(".*?(\\d+\\.\\d+\\.\\d+).*", "$1");
    }

    /**
     * Compares two version strings.
     *
     * @param version1 the first version string.
     * @param version2 the second version string.
     * @return -1 if version1 is less than version2, 1 if version1 is greater than version2, and 0 if they are equal.
     */
    private int compareVersions(final String version1, final String version2) {
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");
        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int part1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
            int part2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;
            if (part1 < part2) return -1;
            if (part1 > part2) return 1;
        }
        return 0;
    }
}
