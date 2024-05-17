package fr.softsf.sudofx2024.utils.os;

public final class OsDynamicFolders {
    private final IOsFoldersFactory iOsFolderFactory;

    public OsDynamicFolders(final String os) throws IllegalArgumentException {
        iOsFolderFactory = createOsFolderFactory(os);
    }

    private IOsFoldersFactory createOsFolderFactory(final String os) {
        return switch (os) {
            case String s when s.contains("win") -> new WindowsFolderFactory();
            default -> throw new IllegalArgumentException("Windows OS is needed to run SudoFX");
        };
    }

    public IOsFoldersFactory getIOsFoldersFactory() {
        return iOsFolderFactory;
    }

    public interface IOsFoldersFactory {
        String getOsDataFolderPath();
        String getOsLogsFolderPath();
    }
}
