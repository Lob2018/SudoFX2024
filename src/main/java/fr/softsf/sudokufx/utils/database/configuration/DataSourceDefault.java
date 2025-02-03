package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudokufx.utils.os.OsFolderFactoryManager;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.utils.MyEnums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the default profile.
 */
@Configuration
@Profile("default")
@PropertySource("classpath:fr/softsf/sudokufx/application.properties")
@ExcludedFromCoverageReportGenerated
public class DataSourceDefault extends DataSource {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final ApplicationKeystore keystore) {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        this.setJdbcUrl("jdbc:hsqldb:file:" + osFolderFactoryManager.osFolderFactory().getOsDataFolderPath() + "/" + DATABASE_NAME.getPath() + ";shutdown=true");
        this.setPoolName("SudokuFXHikariConnection");
        return super.hikariDataSource(keystore);
    }
}
