package fr.softsf.sudokufx.utils;

import fr.softsf.sudokufx.interfaces.ISceneProvider;
import javafx.scene.Scene;
import lombok.Getter;

/**
 * Manages dynamic font sizing based on the dimensions of a JavaFX Scene. This
 * class automatically adjusts the font size of the Scene's root node when the
 * Scene's dimensions change.
 */
public final class DynamicFontSize implements ISceneProvider {

    // The JavaFX Scene to which dynamic font sizing will be applied
    private Scene scene = getScene();

    /**
     * The current font size calculated based on the Scene's dimensions.
     */
    @Getter
    private double currentFontSize;

    /**
     * Constructs a DynamicFontSize instance and initializes Scene listeners.
     */
    public DynamicFontSize() {
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Constructs a DynamicFontSize instance and initializes Scene listeners for testing purposes only.
     */
    DynamicFontSize(Scene scene) {
        this.scene = scene;
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Updates the font size based on the current Scene dimensions.
     * The font size is calculated as 2.19% of the smaller dimension (width or height) of the Scene.
     * This method is called automatically when the Scene's dimensions change.
     */
    public void updateFontSize() {
        currentFontSize = Math.min(scene.getWidth(), scene.getHeight()) * 0.0219;
        scene.getRoot().setStyle("-fx-font-size: " + currentFontSize + "px;");
    }

}
