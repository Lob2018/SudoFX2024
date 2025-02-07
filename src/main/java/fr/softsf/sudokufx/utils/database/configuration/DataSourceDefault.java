package fr.softsf.sudokufx.utils.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.interfaces.IKeystore;
import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
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
    HikariDataSource hikariDataSource(final IKeystore iKeystore) {
        IOsFolderFactory iOsFolderFactory = new OsFolderFactoryManager().iOsFolderFactory();
        this.setJdbcUrl("jdbc:hsqldb:file:" + iOsFolderFactory.getOsDataFolderPath() + "/" + DATABASE_NAME.getPath() + ";shutdown=true");
        this.setPoolName("SudokuFXHikariConnection");
        return super.hikariDataSource(iKeystore);
    }
}
