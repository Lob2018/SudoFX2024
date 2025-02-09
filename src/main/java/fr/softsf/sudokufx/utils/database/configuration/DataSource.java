package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.interfaces.IKeystore;
import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import fr.softsf.sudokufx.utils.MyLogback;
import lombok.Setter;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_MIGRATION_PATH;

/**
 * Abstract configuration class for setting up the application's data source.
 * This class provides common configurations for different data source implementations.
 */
@Setter
@Configuration
@ExcludedFromCoverageReportGenerated
public abstract class DataSource {

    private String jdbcUrl;
    private String poolName;

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
     * This method sets up a connection pool for HSQLDB database.
     *
     * @param iKeystore        Application keystore for secure storage
     * @param iOsFolderFactory Factory for creating OS-specific folder paths.
     * @return Configured DataSource
     */
    @Bean
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final IKeystore iKeystore, IOsFolderFactory iOsFolderFactory) {
        iKeystore.setupApplicationKeystore();
        final HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);
        config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(iKeystore.getUsername());
        config.setPassword(iKeystore.getPassword());
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
