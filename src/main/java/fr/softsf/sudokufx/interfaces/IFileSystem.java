package fr.softsf.sudokufx.interfaces;

import java.nio.file.Path;

/**
 * Interface defining a method to recursively deletes a folder and its contents.
 * The folder path must end with the specified String.
 */
public interface IFileSystem {
    /**
     * Recursively deletes a folder and its contents if the folder path ends
     * with the specified string.
     *
     * @param folderPath      The path of the folder to be deleted.
     * @param mustEndWithThat The string that the folder path must end with for
     *                        deletion to proceed.
     * @return true if the folder was successfully deleted, false otherwise.
     */
    boolean deleteFolderRecursively(Path folderPath, String mustEndWithThat);
}
