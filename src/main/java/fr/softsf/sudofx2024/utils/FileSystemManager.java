package fr.softsf.sudofx2024.utils;

import fr.softsf.sudofx2024.interfaces.IFileSystem;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

@Slf4j
public final class FileSystemManager implements IFileSystem {
    @Override
    public boolean deleteFolder(final Path folderPath, final String mustEndWithThat) {
        if (folderPath.endsWith(mustEndWithThat)) {
            log.info("▓▓▓▓ The directory path is correct :{}", folderPath);
            try (Stream<Path> stream = Files.walk(folderPath)) {
                stream.sorted(Comparator.reverseOrder())
                        .forEach(this::deleteFile);
                return true;
            } catch (Exception e) {
                log.error(String.format("██ Exception catch from deleteFolder : %s", e.getMessage()), e);
            }
        } else {
            log.info("▓▓▓▓ The directory path is not correct :{}", folderPath);
        }
        return false;
    }

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
