package fr.softsf.sudofx2024.utils.os;

public class OsDynamicFolders {
    private final IOsFoldersFactory iOsFolderFactory;

    public OsDynamicFolders(String os) throws IllegalArgumentException {
        iOsFolderFactory = createOsFolderFactory(os);
    }

    private IOsFoldersFactory createOsFolderFactory(String os) {
        return switch (os) {
            case String s when s.contains("win") -> new WindowsFolderFactory();
            default -> throw new IllegalArgumentException("Windows OS is needed to run SudoFX");
        };
    }

    public final IOsFoldersFactory getIOsFoldersFactory() {
        return iOsFolderFactory;
    }

    public interface IOsFoldersFactory {
        String getOsDataFolderPath();
        String getOsLogsFolderPath();
    }
}
