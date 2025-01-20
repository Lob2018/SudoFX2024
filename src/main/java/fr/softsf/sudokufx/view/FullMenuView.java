package fr.softsf.sudokufx.view;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.service.FxmlService;
import fr.softsf.sudokufx.utils.MyEnums;
import fr.softsf.sudokufx.view.components.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.FullMenuViewModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * View class for the full menu screen of the Sudoku application. This class is
 * responsible for displaying and managing the full menu UI.
 */
@Slf4j
public final class FullMenuView implements SudoMain.IPrimaryStageView {

    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private final Text text1 = new Text("Helloj! ");
    private final Stage primaryStage = new Stage();
    @Autowired
    FullMenuViewModel fullMenuViewModel;
    @Autowired
    private FxmlService fxmlService;
    @FXML
    @Autowired
    private ToasterVBox toaster;
    @FXML
    private Label welcomeText;
    @FXML
    private Button buttonHello;
    private Text text2 = new Text("\ue86c");

    /**
     * Initializes the full menu view. This method is automatically called by
     * JavaFX after loading the FXML.
     */
    @FXML
    private void initialize() {
        text1.getStyleClass().add("root");
        text2.getStyleClass().add("material");
        HBox hbox = new HBox(text1, text2);
        hbox.setStyle("-fx-background-color: #FF0000;");
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        buttonHello.setGraphic(hbox);
        buttonHello.setStyle("-fx-background-color: #00FF00;");
        //
        welcomeText.textProperty().bindBidirectional(fullMenuViewModel.welcomeProperty());
    }

    /**
     * Handles the click event of the Hello button. Displays various toast
     * notifications and triggers a test in the view model.
     *
     * @param event The action event triggered by clicking the button
     */
    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        toaster.addToastWithDuration("INFO 🔹 Work in progress... 🔹", MyEnums.ToastLevels.INFO, 6000);
        toaster.addToast("WARN", MyEnums.ToastLevels.WARN);
        toaster.addToast("(130 CHAR) ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR", MyEnums.ToastLevels.ERROR);
        fullMenuViewModel.test();

//        fxmlService.setRootByFXMLName("crashscreen-view");
    }

    /**
     * Configures the primary stage for the full menu view.
     */
    private void openingConfigureStage() {
        primaryStage.getIcons().add(new Image((Objects.requireNonNull(SudoMain.class.getResource(MyEnums.Paths.LOGO_SUDO_PNG_PATH.getPath()))).toExternalForm()));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(SudoMain.getScene());
        primaryStage.centerOnScreen();
    }

    /**
     * Maximizes the primary stage to fill the primary screen.
     */
    private void openingMaximizePrimaryStage() {
        Rectangle2D r = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(r.getMinX());
        primaryStage.setY(r.getMinY());
        primaryStage.setWidth(r.getWidth());
        primaryStage.setHeight(r.getHeight());
    }

    /**
     * Applies a fade-in effect to the given node.
     *
     * @param node The node to apply the fade-in effect to
     */
    private void openingFadeIn(final Node node) {
        primaryStage.setAlwaysOnTop(true);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        primaryStage.setMaximized(true);
    }

    /**
     * Shows the primary stage.
     */
    private void openingShowStage() {
        primaryStage.show();
    }

    /**
     * Opens the main stage and handles the transition from splash screen to
     * full menu.
     *
     * @param iSplashScreenView The splash screen view interface
     */
    @Override
    public void openingMainStage(final ISplashScreenView iSplashScreenView) {
        openingConfigureStage();
        openingMaximizePrimaryStage();
        openingFadeIn(SudoMain.getScene().getRoot());
        openingShowStage();
        iSplashScreenView.hideSplashScreen();
        primaryStage.setAlwaysOnTop(false);
    }
}
