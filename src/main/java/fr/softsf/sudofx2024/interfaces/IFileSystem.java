package fr.softsf.sudofx2024.interfaces;

import java.nio.file.Path;

public interface IFileSystem {
    boolean deleteFolder(Path folderPath, String mustEndWithThat);
}
