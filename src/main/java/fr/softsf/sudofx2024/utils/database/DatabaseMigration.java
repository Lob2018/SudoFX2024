package fr.softsf.sudofx2024.utils.database;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.interfaces.IKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;

import static fr.softsf.sudofx2024.utils.MyEnums.Paths.DATABASE_MIGRATION_PATH;

@Slf4j
public final class DatabaseMigration {

    @ExcludedFromCoverageReportGenerated
    private DatabaseMigration() {
    }

    /**
     * Configure Flyway migration
     *
     * @param iKeystore         The Keystore
     * @param iOsFolderFactory_ The folder factory
     */
    public static void configure(final IKeystore iKeystore, final OsDynamicFolders.IOsFoldersFactory iOsFolderFactory_) {
        log.info("\n▓▓ Start of Flyway migration");
        String databasePath = "jdbc:hsqldb:file:" + iOsFolderFactory_.getOsDataFolderPath() + String.format("/%s", "sudofx2024db");
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
            log.error(String.format("██ FlywayException catch : %s", e.getMessage()), e);
            throw e;
        }
    }

    /**
     * Format Flyway information
     *
     * @param flyway The Flyway
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
