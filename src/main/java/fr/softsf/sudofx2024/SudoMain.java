package fr.softsf.sudofx2024;

import ch.qos.logback.core.util.Loader;
import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudofx2024.utils.DynamicFontSize;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Objects;

import static fr.softsf.sudofx2024.utils.ExceptionTools.getSQLInvalidAuthorizationSpecException;
import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
@SpringBootApplication
@ComponentScan({
        "com.gluonhq.ignite.spring",
        "fr.softsf.sudofx2024.*",
})
public class SudoMain extends Application {

    private final SpringContext context = new SpringContext(this);

    @Autowired
    private FXMLLoader fxmlLoader;

    private ISplashScreenView isplashScreenView;
    private IPrimaryStageView iPrimaryStageView;

    private Throwable t=null;

    @Getter
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    private void initializeScene() throws IOException {
        fxmlLoader.setLocation(getFXMLLoader("splashscreen-view").getLocation());
        scene = new Scene(fxmlLoader.load(), -1, -1, Color.TRANSPARENT);
        scene.getStylesheets().add((Objects.requireNonNull(SudoMain.class.getResource(RESOURCES_CSS_PATH.getPath()))).toExternalForm());
    }

    @Override
    public void start(final Stage primaryStageP) {
        try {

            // TODO MY SPLASH SCREEN MANNUALLY (sans FXML)
            Text text = new Text("Hello, JavaFX!");
            // Configuration du texte
            text.setFont(Font.font("Verdana", 20));
            text.setFill(Color.RED);
            ProgressIndicator progressIndicator = new ProgressIndicator();
            StackPane stackPane=new StackPane();
            stackPane.getChildren().add(text);
            stackPane.getChildren().add(progressIndicator);
            // Création de la scène et ajout du texte
            scene = new Scene(stackPane, 300, 200, Color.TRANSPARENT);
            scene.getStylesheets().add((Objects.requireNonNull(SudoMain.class.getResource(RESOURCES_CSS_PATH.getPath()))).toExternalForm());
            primaryStageP.setScene(scene);
            primaryStageP.setTitle("JavaFX App");
            primaryStageP.show();


            Thread.ofVirtual().start(() -> {
                Throwable throwable = null;
                try {
                    context.init(() -> SpringApplication.run(SudoMain.class));
                } catch (Exception e) {
                    throwable = e;
                    fxmlLoader=new FXMLLoader();
                } finally {
                    Throwable finalThrowable = throwable;
                    Platform.runLater(() -> {
                        try {
                            initializeScene();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        isplashScreenView = fxmlLoader.getController();
                        new DynamicFontSize(scene);
                        primaryStageP.hide();
                        // TODO gérer fullmenuView ET crashView


                        isplashScreenView.showSplashScreen();
                        loadingThreadExecutorResult(finalThrowable, System.currentTimeMillis());
                        // TODO Get & Set latest saved view from initializationAsynchronousTask
                    });
                }
            });








        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside start() : %s", e.getMessage()), e);
            throw new RuntimeException(e);
        }
    }

    private void loadingThreadExecutorResult(final Throwable throwable, final long startTime) {
        if (throwable == null) {
            final long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
            PauseTransition pause = getPauseTransition("fullmenu-view", minimumTimelapse);
            pause.play();
        } else {
            errorInLoadingThread(throwable);
        }
    }

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

    private static void sqlInvalidAuthorization(Exception e, SQLInvalidAuthorizationSpecException sqlException) {
        log.error(String.format("██ SQLInvalidAuthorizationSpecException catch : %s", e.getMessage()), e);
        String sqlState = sqlException.getSQLState();
        if ("28000".equals(sqlState) || "28501".equals(sqlState)) {
            log.error(String.format("██ SQLInvalidAuthorizationSpecException with sqlstate==(28000||28501) catch : %s", e.getMessage()), e);
            log.info(String.format("%n%n%s", SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION.getLogBackMessage()));
        }
    }

    private PauseTransition getPauseTransition(String fxmlName, long minimumTimelapse) {
        PauseTransition pause = new PauseTransition(Duration.millis(minimumTimelapse));
        pause.setOnFinished(e -> {
            setRootByFXMLName(fxmlName);
            iPrimaryStageView = fxmlLoader.getController();
            iPrimaryStageView.showPrimaryStage(isplashScreenView);
        });
        return pause;
    }


    public void setRootByFXMLName(final String fxml) {
        try {
            clearFXMLLODER();
            scene.setRoot(getFXMLLoader(fxml).load());
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside setRootByFXMLName(String) : %s", e.getMessage()), e);
            Platform.exit();
        }
    }

    private void clearFXMLLODER() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
    }

    private FXMLLoader getFXMLLoader(final String fxml) {
        fxmlLoader.setLocation(SudoMain.class.getResource(RESOURCES_FXML_PATH.getPath() + fxml + ".fxml"));
        return fxmlLoader;
    }

    public interface IPrimaryStageView {
        void showPrimaryStage(ISplashScreenView iSplashScreenView);
    }

    public interface ISplashScreenView {
        void hideSplashScreen();

        void showSplashScreen();
    }
}


