package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.utils.MyLogback;
import fr.softsf.sudokufx.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudokufx.utils.os.OsFolderFactoryManager;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_MIGRATION_PATH;
import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Overloaded configuration class for tests setting up dynamic data sources and related beans.
 */
@Configuration
@Profile("test")
@PropertySource("classpath:application-test.properties")
public class DataSourceConfigForTests {

    /**
     * Initializes Logback logging framework.
     *
     * @param myLogback Custom Logback configuration bean
     * @return Always returns 0
     */
    @Bean
    int logbackInitialization(final MyLogback myLogback) {
        myLogback.printLogEntryMessage();
        return 0;
    }

    /**
     * Creates and configures the main DataSource for the application. This bean
     * depends on logbackInitialization to ensure Logback is properly set up.
     * This method sets up a connection pool for an in-memory HSQLDB database.
     *
     * @param osFolderFactory Factory for creating OS-specific folders
     * @param keystore        Application keystore for secure storage
     * @return Configured DataSource
     */
    @Bean
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final OsFolderFactoryManager osFolderFactory, final ApplicationKeystore keystore) {
        keystore.setupApplicationKeystore();
        final HikariConfig config = new HikariConfig();
        config.setPoolName("SudoFXTestHikariConnection");
        config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        config.setJdbcUrl("jdbc:hsqldb:mem:"+DATABASE_NAME.getPath() + "Test" + ";shutdown=true");
        config.setUsername(keystore.getUsername());
        config.setPassword(keystore.getPassword());
        config.setMaximumPoolSize(2);
        config.setMinimumIdle(1);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    /**
     * Configures Flyway with a specified location for migration scripts.
     * <p>
     * This method initializes a Flyway instance that will manage database migrations
     * using the provided data source. It specifies the location of the migration scripts
     * which Flyway will execute to ensure the database schema is up-to-date.
     *
     * @param hikariDataSource the HikariDataSource used by Flyway to connect to the database.
     *                         This data source should be properly configured with the necessary
     *                         connection details (URL, username, password).
     * @return a Flyway instance configured with the specified data source and migration script location.
     * The initMethod "migrate" will be called automatically after the bean is created,
     * applying any pending migrations to the database.
     */
    @Bean(initMethod = "migrate")
    Flyway flyway(final HikariDataSource hikariDataSource) {
        return Flyway.configure()
                .dataSource(hikariDataSource)
                .locations("classpath:" + DATABASE_MIGRATION_PATH.getPath())
                .load();
    }
}
