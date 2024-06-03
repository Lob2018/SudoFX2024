package fr.softsf.sudofx2024.utils.hibernate;

import fr.softsf.sudofx2024.interfaces.IHibernateConfiguration;
import fr.softsf.sudofx2024.interfaces.IOsFoldersFactory;
import fr.softsf.sudofx2024.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;


import java.util.Objects;

public class H2SessionFactoryConfigurator implements IHibernateConfiguration {
    public static final String SUDOFX_DB_NAME = "sudofx2024db";
    private final String dataFolderPath;
    private static final Logger logger = LogManager.getLogger(H2SessionFactoryConfigurator.class);

    public H2SessionFactoryConfigurator(IOsFoldersFactory iOsFolderFactory) {
        dataFolderPath = iOsFolderFactory.getOsDataFolderPath() + "/" + SUDOFX_DB_NAME;
    }




    @Override
    public SessionFactory getSessionFactory() {
        try {
            String script = Objects.requireNonNull(this.getClass().getResource("/fr/softsf/sudofx2024/scripts/sudofx2024db.mv.db")).toExternalForm().substring(6);

            System.out.println("~~~~~~~~~~~~~~"+dataFolderPath);
            System.out.println("~~~~~~~~~~~~~~"+script);

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySetting(Environment.URL, "jdbc:h2:" + dataFolderPath)
                    .applySetting(Environment.JAKARTA_JDBC_USER, "sa")
                    .applySetting(Environment.JAKARTA_JDBC_PASSWORD, "")
                    .applySetting(Environment.JAKARTA_HBM2DDL_CREATE_SCRIPT_SOURCE, script)
                    .applySetting(Environment.HBM2DDL_AUTO, "update")
                    .applySetting(Environment.SHOW_SQL, "true")
                    .applySetting(Environment.FORMAT_SQL, "true")
                    .applySetting(Environment.USE_SQL_COMMENTS, "true")
                    .build();

////            1
//            Metadata metadata = new MetadataSources(standardRegistry).buildMetadata();


//            2
            MetadataSources metadataSources = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(SoftwareModel.class)
                    .addAnnotatedClass(PlayerLanguageModel.class)
                    .addAnnotatedClass(BackgroundModel.class)
                    .addAnnotatedClass(GameLevelModel.class)
                    .addAnnotatedClass(GridModel.class)
                    .addAnnotatedClass(PlayerModel.class)
                    .addAnnotatedClass(GameModel.class)
                    ;
            Metadata metadata = metadataSources.buildMetadata();


////            3
//            MetadataSources metadataSources = new MetadataSources(standardRegistry)
//                    .addPackage("../java/fr/softsf/sudofx2024/model"); // Assuming your entities are in this package
//            Metadata metadata = metadataSources.buildMetadata();



            return metadata.getSessionFactoryBuilder().build();
            // Ajouter d'autres configurations ici si nécessaire
        } catch (Error e) {
            logger.error("Exception catch inside H2SessionFactoryConfigurator configure() : " + e.getMessage(), e);
            throw e;
        }
    }
}
