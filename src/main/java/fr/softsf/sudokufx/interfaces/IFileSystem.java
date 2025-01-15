package fr.softsf.sudokufx.interfaces;

import java.nio.file.Path;

public interface IFileSystem {
    /**
     * Delete the folder recursively
     * @param folderPath The folder path
     * @param mustEndWithThat The folder path should end with that
     * @return The result of deletion
     */
    boolean deleteFolderRecursively(Path folderPath, String mustEndWithThat);
}
