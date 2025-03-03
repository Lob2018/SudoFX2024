package fr.softsf.sudokufx;

import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudokufx.enums.LogBackTxt;
import fr.softsf.sudokufx.exceptions.ExceptionTools;
import fr.softsf.sudokufx.interfaces.IMainStageView;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.service.FxmlService;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.view.SplashScreenView;
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
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.SQLInvalidAuthorizationSpecException;

/**
 * Main entry point for the Sudo application, responsible for initializing
 * the JavaFX interface and the Spring context. This class handles the splash screen,
 * Spring context initialization, and transitions between views.
 * <p>
 * - Initializes the splash screen and Spring context asynchronously.
 * - Handles errors such as SQL authorization exceptions during startup.
 * - Manages dynamic FXML loading and view transitions for SplashScreen CrashScreen and the DefaultScreen.
 *
 * @SpringBootApplication: Bootstraps the Spring context.
 * @EnableAsync: Enables asynchronous processing.
 * @ComponentScan: Scans specified packages for Spring components.
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@ComponentScan({"com.gluonhq.ignite.spring", "fr.softsf.sudokufx.*",})
public class SudoMain extends Application {

    @Getter
    private static Scene scene;
    private final SpringContext context = new SpringContext(this);
    private ISplashScreenView isplashScreenView;
    private IMainStageView iMainStageView;
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
            log.info("\n\n{}", LogBackTxt.SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION.getLogBackMessage());
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
     * 0. Sets the language based on the host environment.
     * 1. Initializes the splash screen view with the provided stage.
     * 2. Sets up the main scene for the splash screen.
     * 3. Creates and starts a background task to initialize the Spring context,
     * ensuring the JavaFX application thread remains unblocked.
     * 4. Sets a handler to manage actions upon successful completion of the initialization.
     * 5. Sets a handler to manage errors that occur during the initialization process.
     *
     * @param splashScreenStage The primary stage for displaying the splash screen.
     */
    @Override
    public void start(final Stage splashScreenStage) {
        try {
            I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
            isplashScreenView = new SplashScreenView(splashScreenStage);
            initScene(splashScreenStage);
            Task<Void> springContextTask = createSpringContextTask();
            new Thread(springContextTask).start();
            long startTime = System.currentTimeMillis();
            springContextTask.setOnSucceeded(event -> handleSpringContextTaskSuccess(startTime));
            springContextTask.setOnFailed(event -> {
                Throwable th = springContextTask.getException();
                handleSpringContextTaskFailed(th);
            });
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
                context.init(() -> SpringApplication.run(SudoMain.class));
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
            log.error("██ Exception caught after Spring Context initialization with FXML : {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Handles errors that occur during the Spring context initialization task.
     * This method is called when the initialization task fails.
     * <p>
     * It attempts to initialize the FXML service, logs the error, and manages
     * the response based on the type of exception encountered. If the exception
     * is related to SQL authorization, it displays an appropriate error screen;
     * otherwise, it exits the application.
     *
     * @param throwable The exception that occurred during the initialization process.
     */
    private void handleSpringContextTaskFailed(Throwable throwable) {
        initializeFxmlService();
        log.error("██ Error in splash screen initialization thread : {}", throwable.getMessage(), throwable);
        SQLInvalidAuthorizationSpecException sqlInvalidAuthorizationSpecException = ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(throwable);
        if (sqlInvalidAuthorizationSpecException == null) {
            Platform.exit();
        } else {
            sqlInvalidAuthorization((Exception) throwable, sqlInvalidAuthorizationSpecException);
            PauseTransition pause = getPauseTransition("crashscreen-view", 0);
            pause.play();
        }
    }

    /**
     * Initialize the FxmlService if needed and set his DynamicFontSize
     */
    private void initializeFxmlService() {
        if (fxmlService == null) {
            fxmlService = new FxmlService(new FXMLLoader());
        }
        fxmlService.setDynamicFontSize();
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
            iMainStageView = fxmlService.getController();
            iMainStageView.openingMainStage(isplashScreenView);
        });
        return pause;
    }
}








