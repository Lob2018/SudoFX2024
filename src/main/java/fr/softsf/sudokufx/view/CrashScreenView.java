package fr.softsf.sudokufx.view;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;
import fr.softsf.sudokufx.interfaces.*;
import fr.softsf.sudokufx.utils.FileSystemManager;
import fr.softsf.sudokufx.utils.I18n;
import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.configuration.os.OsFolderFactoryManager;
import fr.softsf.sudokufx.utils.IFileSystem;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATA_FOLDER;
import static fr.softsf.sudokufx.utils.MyEnums.Paths.LOGO_SUDO_PNG_PATH;
import static fr.softsf.sudokufx.utils.MyEnums.ScreenSize.DISPOSABLE_SIZE;

/**
 * View class for the crash screen without business logic. This class is
 * responsible for displaying and managing the crash screen UI.
 */
@Slf4j
public final class CrashScreenView implements IMainStageView, ISceneProvider {

    private static final IOsFolderFactory iOsFolderFactory = new OsFolderFactoryManager().iOsFolderFactory();
    private static final IFileSystem iFileSystem = new FileSystemManager();
    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.5;

    private final Stage crashscreenStage = new Stage();
    private final DropShadow dropShadow = new DropShadow();
    private final Stage primaryStage = new Stage();
    // FXML injected components
    @FXML
    private VBox crashscreenvbox;
    @FXML
    private Region crashscreenvboxTophboxRegionsudosvg;
    @FXML
    private Label crashscreenvboxTophboxNamelabel;
    @FXML
    private HBox crashscreenvboxCenterhboxHbox;
    @FXML
    private Label crashscreenvboxCenterhboxLabel;
    @FXML
    private Label crashscreenvboxCenterhboxLabel2;
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonReset;
    @FXML
    private Label crashscreenvboxBottomhboxYearlabel;
    @FXML
    private Label crashscreenvboxBottomhboxVersionlabel;
    private double crashScreenFontSize;

    /**
     * Handles the close button click event. Logs the action and exits the
     * application.
     */
    @FXML
    private void closeButtonClick() {
        log.info("▓▓▓▓ The user choose to close the application");
        hidecrashscreen();
        Platform.exit();
    }

    /**
     * Handles the reset button click event. Attempts to delete the application
     * data directory and then exits the application.
     */
    @FXML
    private void resetButtonClick() {
        log.info("▓▓▓▓ The user choose to reset the application data");
        Path pathToDelete = Paths.get(iOsFolderFactory.getOsDataFolderPath());
        if (iFileSystem.deleteFolderRecursively(pathToDelete, DATA_FOLDER.getPath())) {
            log.info("▓▓▓▓ The directory is deleted");
        } else {
            log.info("▓▓▓▓ The directory isn't deleted");
        }
        hidecrashscreen();
        Platform.exit();
    }

    /**
     * Applies a fade-in effect to the given node.
     *
     * @param node The node to apply the fade-in effect to
     */
    private void fadeIn(final Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        primaryStage.setMaximized(true);
    }

    /**
     * Opens the main stage and handles the transition from splash screen to
     * crash screen.
     *
     * @param iSplashScreenView The splash screen view interface
     */
    @Override
    public void openingMainStage(ISplashScreenView iSplashScreenView) {
        fadeIn(getScene().getRoot());
        showcrashscreen();
        iSplashScreenView.hideSplashScreen();
    }

    /**
     * Initializes the crash screen components. This method is automatically
     * called by JavaFX after loading the FXML.
     */
    @FXML
    private void initialize() {
        final Color crashDefaultFontColor = Color.web("#ffffff");
        crashscreenStage.getIcons().add(new Image((Objects.requireNonNull(SudoMain.class.getResource(LOGO_SUDO_PNG_PATH.getPath()))).toExternalForm()));
        crashscreenStage.initStyle(StageStyle.UNDECORATED);
        crashscreenStage.centerOnScreen();
        crashscreenvbox.setPrefWidth(DISPOSABLE_SIZE.getSize() * .7);
        crashscreenvbox.setPrefHeight(DISPOSABLE_SIZE.getSize() * .7);
        crashScreenFontSize = DISPOSABLE_SIZE.getSize() * 0.02;
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(crashScreenFontSize / 2);
        dropShadow.setOffsetX(crashScreenFontSize / 8);
        dropShadow.setOffsetY(crashScreenFontSize / 8);
        crashscreenvboxTophboxNamelabel.setText(JVMApplicationProperties.INSTANCE.getAppName());
        crashscreenvboxTophboxNamelabel.setTextFill(crashDefaultFontColor);
        setCrashscreenvboxTophboxLogosudosvg();
        crashscreenvboxCenterhboxLabel.setText(I18n.INSTANCE.getValue("crashscreen.message"));
        crashscreenvboxCenterhboxLabel.setWrapText(true);
        crashscreenvboxCenterhboxLabel.setTextFill(crashDefaultFontColor);
        crashscreenvboxCenterhboxLabel2.setText(I18n.INSTANCE.getValue("crashscreen.extramessage") + "\n" + iOsFolderFactory.getOsDataFolderPath());
        crashscreenvboxCenterhboxLabel2.setWrapText(true);
        crashscreenvboxCenterhboxLabel2.setTextFill(crashDefaultFontColor);
        buttonReset.setText(I18n.INSTANCE.getValue("crashscreen.reset"));
        buttonClose.setText(I18n.INSTANCE.getValue("crashscreen.close"));
        crashscreenvboxCenterhboxHbox.setSpacing(crashScreenFontSize);
        crashscreenvboxBottomhboxYearlabel.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        crashscreenvboxBottomhboxVersionlabel.setText(JVMApplicationProperties.INSTANCE.getAppVersion());
        crashscreenvboxBottomhboxYearlabel.setTextFill(crashDefaultFontColor);
        crashscreenvboxBottomhboxVersionlabel.setTextFill(crashDefaultFontColor);
    }

    /**
     * Hides and closes the crash screen stage.
     */
    private void hidecrashscreen() {
        crashscreenStage.hide();
        crashscreenStage.close();
    }

    /**
     * Shows the crash screen stage.
     */
    private void showcrashscreen() {
        final Scene s = getScene();
        crashscreenStage.setScene(s);
        crashscreenStage.setWidth(DISPOSABLE_SIZE.getSize() * .7);
        crashscreenStage.setHeight(DISPOSABLE_SIZE.getSize() * .7);
        crashscreenStage.show();
        s.getRoot().setStyle("-fx-font-size: " + crashScreenFontSize + "px;");
        buttonClose.requestFocus();
    }

    /**
     * Sets up the SVG logo for the crash screen.
     */
    private void setCrashscreenvboxTophboxLogosudosvg() {
        createSVG(crashscreenvboxTophboxRegionsudosvg, "M26.9,53.4c-.86,0-1.73,0-2.6,0l-1.43-.09c-.38,0-.74-.07-1.11-.12l-.35,0c-.82-.09-1.78-.21-2.73-.39a28.17,28.17,0,0,1-3.67-1,19.33,19.33,0,0,1-6.43-3.54l-.39-.35A10.24,10.24,0,0,1,5.5,44.14a10.53,10.53,0,0,1-.77-3.89V37.14c0-3.09,2-5.43,4.59-5.43l6.4.25q-1.08-.45-2-.9c-.46-.21-.9-.43-1.29-.64a19.36,19.36,0,0,1-5.76-4.59,10.55,10.55,0,0,1-2.45-6.89,11.48,11.48,0,0,1,3.43-8.26A15.79,15.79,0,0,1,9.17,9.33a18.86,18.86,0,0,1,5-2.56A44.35,44.35,0,0,1,23,4.7c1.16-.15,2.35-.28,3.53-.36,1.4-.1,2.8-.15,4.17-.15.87,0,1.74,0,2.6.06q.72,0,1.44.09c.37,0,.74.07,1.1.11l.35,0c.82.1,1.78.22,2.74.4a26.52,26.52,0,0,1,3.66,1A19.38,19.38,0,0,1,49,9.39l.38.34a10.12,10.12,0,0,1,2.69,3.73,10.44,10.44,0,0,1,.77,3.88v3.11c0,3.1-2,5.43-4.6,5.43l-6.4-.24c.72.3,1.41.59,2,.89.46.22.9.44,1.29.65A19.49,19.49,0,0,1,51,31.76a10.54,10.54,0,0,1,2.45,6.9A11.46,11.46,0,0,1,50,46.92a14.66,14.66,0,0,1-1.54,1.34,18.64,18.64,0,0,1-5,2.56,43.5,43.5,0,0,1-8.78,2.07c-1.16.16-2.34.28-3.53.37-1.39.09-2.79.14-4.17.14Z", crashScreenFontSize, crashScreenFontSize, 0, 0);
        String strokeWidth = "" + crashScreenFontSize / 17;
        crashscreenvboxTophboxRegionsudosvg.setStyle("-fx-background-color: linear-gradient(to bottom, #FFBE99, #FF4340);-fx-border-color: #1D1D30;-fx-border-width: " + strokeWidth + "px;-fx-border-insets: -" + strokeWidth + " -" + strokeWidth + " -" + strokeWidth + " -" + strokeWidth + ";");
    }

    /**
     * Creates an SVG shape and applies it to a Region.
     *
     * @param region  The Region to apply the SVG shape to
     * @param path    The SVG path data
     * @param width   The width of the SVG
     * @param height  The height of the SVG
     * @param offsetX The X offset of the SVG
     * @param offsetY The Y offset of the SVG
     */
    private void createSVG(final Region region, final String path, final double width, final double height, final double offsetX, final double offsetY) {
        final SVGPath svgPath = new SVGPath();
        svgPath.setContent(path);
        region.setShape(svgPath);
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        region.setTranslateX(offsetX);
        region.setTranslateY(offsetY);
    }
}
