package fr.softsf.sudokufx.interfaces;

import fr.softsf.sudokufx.SudoMain;
import javafx.scene.Scene;

/**
 * Interface ISceneProvider.
 * <p>
 * Provides standardized access to the main scene of a JavaFX application.
 * Classes implementing this interface can use the default method to obtain
 * the scene without directly depending on the main application class.
 */
public interface ISceneProvider {

    /**
     * Retrieves the current scene of the application.
     *
     * @return The active scene, obtained via SudoMain.
     */
    default Scene getScene() {
        return SudoMain.getScene();
    }
}
