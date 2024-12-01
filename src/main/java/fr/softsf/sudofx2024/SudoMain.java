package fr.softsf.sudofx2024;

import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudofx2024.service.FxmlService;
import fr.softsf.sudofx2024.utils.DynamicFontSize;
import fr.softsf.sudofx2024.view.SplashScreenView;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

/**
 * The application main class. This class initializes the
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
    private ISplashScreenView isplashScreenView;
    private IPrimaryStageView iPrimaryStageView;
    @Autowired
    private FxmlService fxmlService;

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
     * Initializes the main scene.
     *
     * @param splashScreenStage The stage used for the splash screen
     */
    private static void initScene(Stage splashScreenStage) {
        scene = splashScreenStage.getScene();
    }

    /**
     * Initializes the application by setting up the splash screen and loading
     * the main application context.
     * <p>
     * This method performs the following:
     * 1. Initializes the splash screen view with the provided stage.
     * 2. Sets up the main scene for the splash screen.
     * 3. Creates and starts a background task to initialize the Spring context,
     * ensuring the JavaFX application thread remains unblocked.
     * 4. Sets a handler to manage actions upon successful completion of the initialization.
     *
     * @param splashScreenStage The primary stage for displaying the splash screen.
     */
    @Override
    public void start(final Stage splashScreenStage) {
        try {
            isplashScreenView = new SplashScreenView(splashScreenStage);
            initScene(splashScreenStage);
            Task<Void> springContextTask = createSpringContextTask();
            new Thread(springContextTask).start();
            long startTime = System.currentTimeMillis();
            springContextTask.setOnSucceeded(event -> handleSpringContextTaskSuccess(startTime));
        } catch (Exception ex) {
            log.error("██ Exception catch inside start() : {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a new Task that initializes the Spring context.
     * This task is intended to be run in a background thread to avoid blocking the JavaFX application thread.
     *
     * @return A Task<Void> that performs the Spring context initialization.
     */
    private Task<Void> createSpringContextTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    context.init(() -> SpringApplication.run(SudoMain.class));
                } catch (Exception ex) {
                    log.error("██ Exception caught during Spring Context initialization: {}", ex.getMessage(), ex);
                    throw new RuntimeException(ex);
                }
                return null;
            }
        };
    }

    /**
     * Handles the success of the Spring context initialization task.
     * This method is called when the initialization task completes successfully.
     *
     * @param startTime The time (in milliseconds) when the initialization started.
     *                  This is used to apply the minimum delay of 1s before starting the transition.
     */
    private void handleSpringContextTaskSuccess(long startTime) {
        try {
            initializeFxmlService();
            long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
            getPauseTransition("fullmenu-view", minimumTimelapse).play();
        } catch (Exception ex) {
            initializeFxmlService();
            errorInLoadingThread(ex);
        }
    }

    /**
     * Initialize the FxmlService if needed and set his DynamicFontSize
     */
    private void initializeFxmlService() {
        if (fxmlService == null) {
            fxmlService = new FxmlService(new FXMLLoader());
        }
        fxmlService.setDynamicFontSize(new DynamicFontSize(scene));
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
            fxmlService.setRootByFXMLName(fxmlName);
            iPrimaryStageView = fxmlService.getController();
            iPrimaryStageView.openingMainStage(isplashScreenView);
        });
        return pause;
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








