package fr.softsf.sudokufx.utils.database.configuration;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.utils.MyLogback;
import org.springframework.context.annotation.*;

/**
 * Configuration class for setting up data sources and related beans for the CDS profile.
 * CDS optimizes the startup and memory management of the application without the JRE.
 */
@Configuration
@Profile("cds")
@PropertySource("classpath:fr/softsf/sudokufx/application-cds.properties")
@ExcludedFromCoverageReportGenerated
public class CdsDataSourceConfig {

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
}

