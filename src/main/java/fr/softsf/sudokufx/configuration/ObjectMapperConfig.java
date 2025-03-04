package fr.softsf.sudokufx.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson's ObjectMapper.
 * This bean provides a pre-configured ObjectMapper with support for Java 8 date/time API
 * and ensures that dates are serialized in ISO-8601 format instead of timestamps.
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Creates and configures an ObjectMapper bean.
     * - Automatically registers available Jackson modules (e.g., JavaTimeModule for LocalDateTime).
     * - Disables writing dates as timestamps to use ISO-8601 format.
     *
     * @return a pre-configured ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}

