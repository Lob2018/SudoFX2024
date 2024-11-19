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
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLInvalidAuthorizationSpecException;

import static fr.softsf.sudofx2024.utils.ExceptionTools.getSQLInvalidAuthorizationSpecException;
import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.RESOURCES_FXML_PATH;

/**
 * Main application class for the Sudoku game. This class initializes the
 * application, manages the splash screen, and handles the transition to the
 * main application view.
 */
@Slf4j
@SpringBootApplication
@PropertySource("classpath:/fr/softsf/sudofx2024/application.properties")
@ComponentScan({"com.gluonhq.ignite.spring", "fr.softsf.sudofx2024.*",})
public class SudoMain extends Application {

    @Getter
    private static Scene scene;
    private final SpringContext context = new SpringContext(this);
    @Autowired
    private FXMLLoader fxmlLoader;
    private ISplashScreenView isplashScreenView;
    private IPrimaryStageView iPrimaryStageView;

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
     * Handles SQL invalid authorization exceptions.
     *
     * @param e            The general exception
     * @param sqlException The specific SQL invalid authorization exception
     */
    private static void sqlInvalidAuthorization(Exception e, SQLInvalidAuthorizationSpecException sqlException) {
        log.error("██ SQLInvalidAuthorizationSpecException catch : {}", e.getMessage(), e);
        String sqlState = sqlException.getSQLState();
        if ("28000".equals(sqlState) || "28501".equals(sqlState)) {
            log.error("██ SQLInvalidAuthorizationSpecException with sqlstate==(28000||28501) catch : {}", e.getMessage(), e);
            log.info("\n\n{}", SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION.getLogBackMessage());
        }
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
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(e -> Platform.runLater(() -> {
                try {
                    long startTime = System.currentTimeMillis();
                    context.init(() -> SpringApplication.run(SudoMain.class));
                    long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
                    getPauseTransition("fullmenu-view", minimumTimelapse).play();
                } catch (Exception ex) {
                    fxmlLoader = new FXMLLoader();
                    errorInLoadingThread(ex);
                }
            }));
            pause.play();
        } catch (Exception e) {
            log.error("██ Exception catch inside start() : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles errors that occur during the loading process.
     *
     * @param throwable The exception that occurred
     */
    private void errorInLoadingThread(Throwable throwable) {
        log.error("██ Error in splash screen initialization thread : {}", throwable.getMessage(), throwable);
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
     * Creates a PauseTransition to delay loading of the next view.
     *
     * @param fxmlName         The name of the FXML file to load
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
    private void setRootByFXMLName(final String fxml) {
        try {
            fxmlLoader.setLocation(SudoMain.class.getResource(RESOURCES_FXML_PATH.getPath() + fxml + ".fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (Exception e) {
            log.error("██ Exception catch when setting root by FXML name : {}", e.getMessage(), e);
            Platform.exit();
        }
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








