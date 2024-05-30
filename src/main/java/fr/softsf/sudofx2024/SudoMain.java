package fr.softsf.sudofx2024;

import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.DynamicFontSize;
import fr.softsf.sudofx2024.utils.MyLogback;
import fr.softsf.sudofx2024.utils.database.hibernate.HSQLDBSessionFactoryConfigurator;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
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
    @Autowired
    WindowsFolderFactory osFolderFactory;
    @Autowired
    MyLogback setupMyLogback;
    @Autowired
    ApplicationKeystore keystore;

    private ISplashScreenView isplashScreenView;
    private IPrimaryStageView iPrimaryStageView;

    @Getter
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        context.init(() -> SpringApplication.run(SudoMain.class));
    }

    private void initializeScene() throws IOException {
        keystore.setupApplicationKeystore();
        fxmlLoader.setLocation(getFXMLLoader("splashscreen-view").getLocation());
        scene = new Scene(fxmlLoader.load(), -1, -1, Color.TRANSPARENT);
        scene.getStylesheets().add((Objects.requireNonNull(SudoMain.class.getResource(RESOURCES_CSS_PATH.getPath()))).toExternalForm());
    }

    @Override
    public void start(final Stage primaryStageP) {
        try {
            initializeScene();
            isplashScreenView = fxmlLoader.getController();
            new DynamicFontSize(scene);
            isplashScreenView.showSplashScreen();
            final long startTime = System.currentTimeMillis();
            Thread.ofVirtual().start(() -> {
                Throwable throwable = null;
                try {
                    loadingFlywayAndHibernate();
                } catch (Exception e) {
                    throwable = e;
                } finally {
                    Throwable finalThrowable = throwable;
                    Platform.runLater(() -> {
                        loadingThreadExecutorResult(finalThrowable, startTime);
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
        stop();
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

    private void loadingFlywayAndHibernate() {
        DatabaseMigration.configure(keystore, osFolderFactory);
        Session session = HibernateSessionFactoryManager.getSessionFactory(new HSQLDBSessionFactoryConfigurator(keystore, osFolderFactory)).openSession();
        session.close();
    }


    @Override
    public void stop() {
        try {
            HibernateSessionFactoryManager.closeSessionFactory();
            log.info("\n▓▓ SessionFactory is successfully closed");
            super.stop();
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside stop() : %s", e.getMessage()), e);
            Platform.exit();
        } finally {
            log.info("\n\n▓▓ Exiting application\n");
        }
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


