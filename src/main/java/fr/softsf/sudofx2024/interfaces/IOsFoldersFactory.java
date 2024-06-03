package fr.softsf.sudofx2024.interfaces;

import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;

public sealed interface IOsFoldersFactory permits WindowsFolderFactory {
    String getOsDataFolderPath();
    String getOsLogsFolderPath();
}
