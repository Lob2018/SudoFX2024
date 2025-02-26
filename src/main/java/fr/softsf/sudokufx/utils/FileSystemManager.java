package fr.softsf.sudokufx.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Manages file system operations, particularly folder deletion. This class
 * implements the IFileSystem interface.
 */
@Slf4j
public final class FileSystemManager implements IFileSystem {

    @Override
    public boolean deleteFolderRecursively(final Path folderPath, final String mustEndWithThat) {
        if (folderPath.endsWith(mustEndWithThat)) {
            log.info("▓▓▓▓ The directory path is correct :{}", folderPath);
            try (Stream<Path> stream = Files.walk(folderPath)) {
                stream.sorted(Comparator.reverseOrder())
                        .forEach(this::deleteFile);
                return true;
            } catch (Exception e) {
                log.error("██ Exception catch from deleteFolder : {}", e.getMessage(), e);
            }
        } else {
            log.info("▓▓▓▓ The directory path is not correct :{}", folderPath);
        }
        return false;
    }

    /**
     * Attempts to delete a single file or directory.
     *
     * @param path The path of the file or directory to be deleted.
     * @return null if the file was successfully deleted, otherwise returns the
     * Exception that occurred.
     */
    Throwable deleteFile(final Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            log.error("██ Failed to delete file: {}", path, e);
            return e;
        }
        return null;
    }
}
