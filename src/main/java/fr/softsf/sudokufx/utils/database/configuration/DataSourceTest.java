package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.utils.database.keystore.ApplicationKeystore;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the test profile.
 */
@Configuration
@Profile("test")
@PropertySource("classpath:application-test.properties")
@ExcludedFromCoverageReportGenerated
public class DataSourceTest extends DataSource {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final ApplicationKeystore keystore) {
        this.setJdbcUrl("jdbc:hsqldb:mem:" + DATABASE_NAME.getPath() + "Test;DB_CLOSE_DELAY=-1;shutdown=true");
        this.setPoolName("SudokuFXTestHikariConnection");
        return super.hikariDataSource(keystore);
    }
}
