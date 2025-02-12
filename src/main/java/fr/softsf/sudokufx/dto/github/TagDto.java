package fr.softsf.sudokufx.dto.github;

public record TagDto(
        String name,
        String zipball_url,
        String tarball_url,
        CommitDto commit,
        String node_id
) {
}
