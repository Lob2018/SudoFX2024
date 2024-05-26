package fr.softsf.sudofx2024.utils.os;

import org.springframework.stereotype.Component;

@Component
public final class OsDynamicFolders {
    private final WindowsFolderFactory iOsFolderFactory;

    public OsDynamicFolders(final String os) throws IllegalArgumentException {
        iOsFolderFactory = createOsFolderFactory(os);
    }

    private WindowsFolderFactory createOsFolderFactory(final String os) {
        if (os != null && os.contains("win")) {
            return new WindowsFolderFactory();
        } else {
            throw new IllegalArgumentException("Windows OS is needed to run SudoFX");
        }
    }

    public WindowsFolderFactory getIOsFoldersFactory() {
        return iOsFolderFactory;
    }
}
