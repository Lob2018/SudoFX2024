package fr.softsf.sudofx2024;

import fr.softsf.sudofx2024.interfaces.IOsFoldersFactory;
import fr.softsf.sudofx2024.model.SoftwareModel;
import fr.softsf.sudofx2024.service.SoftwareService;
import fr.softsf.sudofx2024.utils.Log;
import fr.softsf.sudofx2024.utils.hibernate.H2SessionFactoryConfigurator;
import fr.softsf.sudofx2024.utils.hibernate.HibernateSessionFactoryManager;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.OsInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class SudoMain extends Application {
    private static final Logger logger = LogManager.getLogger(SudoMain.class);

    @Override
    public void init() throws Exception {
        // OS folders
        IOsFoldersFactory iOsFolderFactory = new OsDynamicFolders(OsInfo.OS_NAME).getIOsFoldersFactory();
        // Log4j2 initialization
        new Log(iOsFolderFactory);
        // Hibernate initialization
        Session session = HibernateSessionFactoryManager.getSessionFactory(new H2SessionFactoryConfigurator(iOsFolderFactory)).openSession();
        session.close();
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        try {
            // The scene
            Parent sudoView = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/fr/softsf/sudofx2024/fxml/sudo-view.fxml")));
            Scene scene = new Scene(sudoView, 300, 275);

            /////////////////

            SoftwareService softwareService=new SoftwareService();
            System.out.println("############# "+softwareService.createSoftware());
            System.out.println("############# "+softwareService.getSoftware());

            /////////////////
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            logger.error("Exception catch inside start(Stage stage) : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void stop() throws Exception {
        try {
            HibernateSessionFactoryManager.closeSessionFactory();
        } catch (Exception e) {
            logger.error("Exception catch inside stop() : " + e.getMessage(), e);
            throw e;
        } finally {
            super.stop();
        }
    }
}


