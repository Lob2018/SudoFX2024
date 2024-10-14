package fr.softsf.sudofx2024;

import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudofx2024.utils.DynamicFontSize;
import fr.softsf.sudofx2024.view.SplashScreenView;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLInvalidAuthorizationSpecException;

import static fr.softsf.sudofx2024.utils.ExceptionTools.getSQLInvalidAuthorizationSpecException;
import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

/**
 * Main application class for the Sudoku game. This class initializes the
 * application, manages the splash screen, and handles the transition to the
 * main application view.
 */
@Slf4j
@SpringBootApplication
@ComponentScan({
    "com.gluonhq.ignite.spring",
    "fr.softsf.sudofx2024.*",})
public class SudoMain extends Application {

    private final SpringContext context = new SpringContext(this);

    @Autowired
    private FXMLLoader fxmlLoader;

    private ISplashScreenView isplashScreenView;
    private IPrimaryStageView iPrimaryStageView;

    @Getter
    private static Scene scene;

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the main scene and sets up dynamic font sizing.
     *
     * @param splashScreenStage The stage used for the splash screen
     */
    public static void initScene(Stage splashScreenStage) {
        scene = splashScreenStage.getScene();
        new DynamicFontSize(scene);
    }

    /**
     * Starts the application, initializes the splash screen, and begins loading
     * the main application.
     *
     * @param splashScreenStage The primary stage for the application
     */
    @Override
    public void start(final Stage splashScreenStage) {
        try {
            isplashScreenView = new SplashScreenView(splashScreenStage);
            initScene(splashScreenStage);
            Platform.runLater(() -> {
                Throwable throwable = null;
                try {
                    context.init(() -> SpringApplication.run(SudoMain.class));
                } catch (Exception e) {
                    throwable = e;
                    fxmlLoader = new FXMLLoader();
                } finally {
                    loadingThreadExecutorResult(throwable, System.currentTimeMillis());
                }
            });
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside start() : %s", e.getMessage()), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the result of the loading thread, either transitioning to the
     * main view or handling errors.
     *
     * @param throwable Any exception that occurred during loading
     * @param startTime The start time of the loading process
     */
    private void loadingThreadExecutorResult(final Throwable throwable, final long startTime) {
        if (throwable == null) {
            final long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
            PauseTransition pause = getPauseTransition("fullmenu-view", minimumTimelapse);
            pause.play();
        } else {
            errorInLoadingThread(throwable);
        }
    }

    /**
     * Handles errors that occur during the loading process.
     *
     * @param throwable The exception that occurred
     */
    private void errorInLoadingThread(Throwable throwable) {
        log.error(String.format("██ Error in splash screen initialization thread : %s", throwable.getMessage()), throwable);
        SQLInvalidAuthorizationSpecException sqlInvalidAuthorizationSpecException = getSQLInvalidAuthorizationSpecException(throwable);
        if (sqlInvalidAuthorizationSpecException == null) {
            Platform.exit();
        } else {
            sqlInvalidAuthorization((Exception) throwable, sqlInvalidAuthorizationSpecException);
            PauseTransition pause = getPauseTransition("crashscreen-view", 0);
            pause.play();
        }
    }

    /**
     * Handles SQL invalid authorization exceptions.
     *
     * @param e The general exception
     * @param sqlException The specific SQL invalid authorization exception
     */
    private static void sqlInvalidAuthorization(Exception e, SQLInvalidAuthorizationSpecException sqlException) {
        log.error(String.format("██ SQLInvalidAuthorizationSpecException catch : %s", e.getMessage()), e);
        String sqlState = sqlException.getSQLState();
        if ("28000".equals(sqlState) || "28501".equals(sqlState)) {
            log.error(String.format("██ SQLInvalidAuthorizationSpecException with sqlstate==(28000||28501) catch : %s", e.getMessage()), e);
            log.info(String.format("%n%n%s", SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION.getLogBackMessage()));
        }
    }

    /**
     * Creates a PauseTransition to delay loading of the next view.
     *
     * @param fxmlName The name of the FXML file to load
     * @param minimumTimelapse The minimum time to pause
     * @return A PauseTransition object
     */
    private PauseTransition getPauseTransition(String fxmlName, long minimumTimelapse) {
        PauseTransition pause = new PauseTransition(Duration.millis(minimumTimelapse));
        pause.setOnFinished(e -> {
            setRootByFXMLName(fxmlName);
            iPrimaryStageView = fxmlLoader.getController();
            iPrimaryStageView.openingMainStage(isplashScreenView);
        });
        return pause;
    }

    /**
     * Sets the root of the scene based on the given FXML file name.
     *
     * @param fxml The name of the FXML file to load
     */
    public void setRootByFXMLName(final String fxml) {
        try {
            clearFXMLLODER();
            scene.setRoot(getFXMLLoader(fxml).load());
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside setRootByFXMLName(String) : %s", e.getMessage()), e);
            Platform.exit();
        }
    }

    /**
     * Clears the current FXML loader.
     */
    private void clearFXMLLODER() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
    }

    /**
     * Gets an FXMLLoader for the specified FXML file.
     *
     * @param fxml The name of the FXML file
     * @return An FXMLLoader object
     */
    private FXMLLoader getFXMLLoader(final String fxml) {
        fxmlLoader.setLocation(SudoMain.class.getResource(RESOURCES_FXML_PATH.getPath() + fxml + ".fxml"));
        return fxmlLoader;
    }

    /**
     * Interface for the primary stage view.
     */
    public interface IPrimaryStageView {

        void openingMainStage(ISplashScreenView iSplashScreenView);
    }

    /**
     * Interface for the splash screen view.
     */
    public interface ISplashScreenView {

        void hideSplashScreen();

        void showSplashScreen();
    }
}