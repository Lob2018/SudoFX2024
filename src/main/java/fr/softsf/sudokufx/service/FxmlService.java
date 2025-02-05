package fr.softsf.sudokufx.service;

import fr.softsf.sudokufx.interfaces.ISceneProvider;
import fr.softsf.sudokufx.utils.DynamicFontSize;
import fr.softsf.sudokufx.utils.MyEnums;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * FxmlService is a Spring service that manages the loading of FXML files
 * and updating the root of the JavaFX scene.
 * This service allows for dynamic changes to the user interface by
 * loading different FXML files based on the application's needs.
 */
@Service
@Slf4j
public class FxmlService implements ISceneProvider {

    private final Scene scene = getScene();

    private final FXMLLoader fxmlLoader;

    @Setter
    private DynamicFontSize dynamicFontSize;

    /**
     * Constructor for FxmlService.
     * <p>
     * This constructor initializes the scene by retrieving the current
     * scene from the main application class.
     * </p>
     */
    public FxmlService(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Sets the root of the scene based on the given FXML file name, and adapt the font size dynamically
     *
     * @param fxml The name of the FXML file to load (without the .fxml extension)
     * @throws IllegalArgumentException if the FXML file is not found
     * @throws RuntimeException         Exits the application if an error occurs while loading the FXML file
     */
    public void setRootByFXMLName(final String fxml) {
        String path = MyEnums.Paths.RESOURCES_FXML_PATH.getPath() + fxml + ".fxml";
        try {
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(getClass().getResource(path));
            scene.setRoot(fxmlLoader.load());
            dynamicFontSize.updateFontSize();
        } catch (Exception e) {
            log.error("██ Exception caught when setting root by FXML name: {} █ The FXML path was: {}", e.getMessage(), path, e);
            Platform.exit();
        }
    }

    /**
     * Retrieves the controller associated with the loaded FXML file.
     *
     * @param <T> the type of the controller
     * @return the controller instance of type T
     * @throws IllegalStateException if the FXML file has not been loaded yet
     */
    public <T> T getController() {
        return fxmlLoader.getController();
    }
}
