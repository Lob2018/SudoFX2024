package fr.softsf.sudofx2024;

import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.DynamicFontSize;
import fr.softsf.sudofx2024.utils.MyLogback;
import fr.softsf.sudofx2024.utils.database.hibernate.HSQLDBSessionFactoryConfigurator;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
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

import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Objects;

import static fr.softsf.sudofx2024.utils.ExceptionTools.getSQLInvalidAuthorizationSpecException;
import static fr.softsf.sudofx2024.utils.MyEnums.Os.OS_NAME;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.*;

@Slf4j
public final class SudoMain extends Application {
    @Getter
    private static final OsDynamicFolders.IOsFoldersFactory iOsFolderFactory = new OsDynamicFolders(OS_NAME.getPath()).getIOsFoldersFactory();
    private static FXMLLoader fxmlLoader = new FXMLLoader();
    private IPrimaryStageView iPrimaryStageView;
    @Getter
    private static Scene scene;
    private static IKeystore iKeystore;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        new MyLogback(iOsFolderFactory);
    }

    private static void initializeScene() throws IOException {
        iKeystore = new ApplicationKeystore(iOsFolderFactory);
        fxmlLoader = getFXMLLoader("splashscreen-view");
        scene = new Scene(fxmlLoader.load(), -1, -1, Color.TRANSPARENT);
        scene.getStylesheets().add((Objects.requireNonNull(SudoMain.class.getResource(RESOURCES_CSS_PATH.getPath()))).toExternalForm());
    }

    @Override
    public void start(final Stage primaryStageP) {
        try {
            initializeScene();
            final ISplashScreenView iSplashScreenView = fxmlLoader.getController();
            new DynamicFontSize(scene);
            iSplashScreenView.showSplashScreen();
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
                        loadingVirtualThreadExecutorResult(finalThrowable, startTime, iSplashScreenView);
                        // TODO Get & Set latest saved view from initializationAsynchronousTask
                    });
                }
            });
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside start() : %s", e.getMessage()), e);
            throw new RuntimeException(e);
        }
    }

    private void loadingVirtualThreadExecutorResult(final Throwable throwable, final long startTime, final ISplashScreenView iSplashScreenView) {
        if (throwable == null) {
            final long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
            PauseTransition pause = getPauseTransition("fullmenu-view", minimumTimelapse, iSplashScreenView);
            pause.play();
        } else {
            errorInLoadingVirtualThread(throwable, iSplashScreenView);
        }
    }

    private void errorInLoadingVirtualThread(Throwable throwable, ISplashScreenView iSplashScreenView) {
        log.error(String.format("██ Error in splash screen initialization virtual thread : %s", throwable.getMessage()), throwable);
        stop();
        SQLInvalidAuthorizationSpecException sqlInvalidAuthorizationSpecException = getSQLInvalidAuthorizationSpecException(throwable);
        if (sqlInvalidAuthorizationSpecException != null) {
            sqlInvalidAuthorization((Exception) throwable, sqlInvalidAuthorizationSpecException);
            PauseTransition pause = getPauseTransition("crashscreen-view", 0, iSplashScreenView);
            pause.play();
        } else { // Handle others exceptions like database lock acquisition failure
            Platform.exit();
        }
    }

    private static void sqlInvalidAuthorization(Exception e, SQLInvalidAuthorizationSpecException sqlException) {
        log.error(String.format("██ SQLInvalidAuthorizationSpecException catch : %s", e.getMessage()), e);
        String sqlState = sqlException.getSQLState();
        // HSQLDB invalid authorization specification codes from keystore
        if ("28000".equals(sqlState) || "28501".equals(sqlState)) {
            log.error(String.format("██ SQLInvalidAuthorizationSpecException with sqlstate==(28000||28501) catch : %s", e.getMessage()), e);
            String message = """      
                    ██ Risk of loss of application data because database authentication is not valid:
                    ██ You can say no and try restarting the application and if the problem persists, reset the application database.
                    ██ In both cases the application will close
                    """;
            log.info(String.format("\n\n%s", message));
        }
    }

    private PauseTransition getPauseTransition(String fxmlName, long minimumTimelapse, ISplashScreenView iSplashScreenView) {
        PauseTransition pause = new PauseTransition(Duration.millis(minimumTimelapse));
        pause.setOnFinished(e -> {
            setRootByFXMLName(fxmlName);
            iPrimaryStageView = fxmlLoader.getController();
            iPrimaryStageView.showPrimaryStage(iSplashScreenView);
        });
        return pause;
    }

    private void loadingFlywayAndHibernate() {
        DatabaseMigration.configure(iKeystore, iOsFolderFactory);
        Session session = HibernateSessionFactoryManager.getSessionFactoryInit(new HSQLDBSessionFactoryConfigurator(iKeystore, iOsFolderFactory)).openSession();
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

    public static void setRootByFXMLName(final String fxml) {
        try {
            clearFXMLLODER();
            scene.setRoot(getFXMLLoader(fxml).load());
        } catch (Exception e) {
            log.error(String.format("██ Exception catch inside setRootByFXMLName(String) : %s", e.getMessage()), e);
            Platform.exit();
        }
    }

    private static void clearFXMLLODER() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
    }

    private static FXMLLoader getFXMLLoader(final String fxml) {
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


