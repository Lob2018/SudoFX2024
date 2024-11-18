package fr.softsf.sudofx2024.utils.database;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.os.OsFolderFactoryManager;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.DATABASE_MIGRATION_PATH;
import static fr.softsf.sudofx2024.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Utility class for managing database migrations using Flyway.
 */
@Slf4j
public final class DatabaseMigration {

    @ExcludedFromCoverageReportGenerated
    private DatabaseMigration() {
    }

    /**
     * Configures and executes Flyway migration.
     *
     * @param iKeystore         The Keystore containing database credentials
     * @param iOsFolderFactory_ The factory for creating OS-specific folders
     */
    public static void configure(final IKeystore iKeystore, final OsFolderFactoryManager.IOsFolderFactory iOsFolderFactory_) {
        log.info("\n▓▓ Start of Flyway migration");
        String databasePath = "jdbc:hsqldb:file:" + iOsFolderFactory_.getOsDataFolderPath() + "/" + DATABASE_NAME.getPath();
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(
                            databasePath,
                            iKeystore.getUsername(),
                            iKeystore.getPassword()
                    )
                    .locations("classpath:" + DATABASE_MIGRATION_PATH.getPath())
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();
            migrationInformation(flyway);
        } catch (FlywayException e) {
            log.error("██ FlywayException catch : {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Formats and logs Flyway migration information.
     *
     * @param flyway The configured Flyway instance
     */
    @ExcludedFromCoverageReportGenerated
    private static void migrationInformation(final Flyway flyway) {
        MigrationInfoService infoService = flyway.info();
        StringBuilder migrationInfo = new StringBuilder();
        MigrationInfo currentMigration = infoService.current();
        if (currentMigration == null) {
            migrationInfo.append("\tNo current migration\n");
        } else {
            migrationInfo.append("\tCurrent version: ").append(currentMigration.getVersion()).append("\n");
        }
        migrationInfo.append("\tApplied migrations (installed rank - version - description - date) :\n");
        for (MigrationInfo migration : infoService.applied()) {
            migrationInfo.append("\t").append(migration.getInstalledRank()).append(" - ").append(migration.getVersion()).append(" - ").append(migration.getDescription()).append(" - ").append(migration.getInstalledOn()).append("\n");
        }
        migrationInfo.append("\tPending migrations (installed rank - version - description - date) :\n");
        for (MigrationInfo migration : infoService.pending()) {
            migrationInfo.append("\t").append(migration.getInstalledRank()).append(" - ").append(migration.getVersion()).append(" - ").append(migration.getDescription()).append(" - ").append(migration.getInstalledOn()).append("\n");
        }
        log.info("\n▓▓ Flyway migration information :\n{}\n▓▓ Flyway migration is complete", migrationInfo);
    }
}
