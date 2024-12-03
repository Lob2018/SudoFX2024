package fr.softsf.sudofx2024.utils.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudofx2024.utils.MyLogback;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsFolderFactoryManager;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Configuration class for setting up dynamic data sources and related beans.
 */
@Configuration
public class DynamicDataSourceConfiguration {

    /**
     * Initializes Logback logging framework.
     *
     * @param myLogback Custom Logback configuration bean
     * @return Always returns 0
     */
    @Bean
    int logbackInitialization(MyLogback myLogback) {
        myLogback.printLogEntryMessage();
        return 0;
    }

    /**
     * Creates and configures the main DataSource for the application. This bean
     * depends on logbackInitialization to ensure Logback is properly set up.
     *
     * @param osFolderFactory Factory for creating OS-specific folders
     * @param keystore        Application keystore for secure storage
     * @return Configured DataSource
     */
    @Bean
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(OsFolderFactoryManager osFolderFactory, ApplicationKeystore keystore) {
        System.setProperty("hibernate.hbm2ddl.auto", "validate");
        keystore.setupApplicationKeystore();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:file:" + osFolderFactory.osFolderFactory().getOsDataFolderPath() + "/" + DATABASE_NAME.getPath() + ";shutdown=true");
        dataSource.setUsername(keystore.getUsername());
        dataSource.setPassword(keystore.getPassword());
        dataSource.setMaximumPoolSize(2);
        dataSource.setMinimumIdle(1);
        dataSource.setAutoCommit(false);
        return dataSource;
    }

    /**
     * Configures Flyway with a specified location for migration scripts.
     * <p>
     * This method initializes a Flyway instance that will manage database migrations
     * using the provided data source. It specifies the location of the migration scripts
     * which Flyway will execute to ensure the database schema is up-to-date.
     *
     * @param hikariDataSource the HikariDataSource used by Flyway to connect to the database.
     *                   This data source should be properly configured with the necessary
     *                   connection details (URL, username, password).
     * @return a Flyway instance configured with the specified data source and migration script location.
     * The initMethod "migrate" will be called automatically after the bean is created,
     * applying any pending migrations to the database.
     */
    @Bean(initMethod = "migrate")
    Flyway flyway(HikariDataSource hikariDataSource) {
        return Flyway.configure()
                .dataSource(hikariDataSource)
                .locations("classpath:fr/softsf/sudofx2024/flyway/scripts/hsqldb/migration")
                .load();
    }
}
