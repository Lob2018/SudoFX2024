package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.utils.database.keystore.ApplicationKeystore;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the CDS profile.
 * CDS optimizes the startup and memory management of the application without the JRE.
 */
@Configuration
@Profile("cds")
@PropertySource("classpath:fr/softsf/sudokufx/application-cds.properties")
@ExcludedFromCoverageReportGenerated
public class DataSourceCds extends DataSource {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final ApplicationKeystore keystore) {
        this.setJdbcUrl("jdbc:hsqldb:mem:" + DATABASE_NAME.getPath() + "CDS;DB_CLOSE_DELAY=-1;shutdown=true");
        this.setPoolName("SudokuFXCDSHikariConnection");
        return super.hikariDataSource(keystore);
    }
}

