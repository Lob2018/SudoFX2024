package fr.softsf.sudofx2024.interfaces;

import java.nio.file.Path;

public interface IFileSystem {
    /**
     * Delete the folder recursively
     * @param folderPath The folder path
     * @param mustEndWithThat The folder path should end with this
     * @return The result of deletion
     */
    boolean deleteFolderRecursively(Path folderPath, String mustEndWithThat);
}
