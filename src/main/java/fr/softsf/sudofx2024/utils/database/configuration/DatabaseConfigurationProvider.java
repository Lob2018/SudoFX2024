package fr.softsf.sudofx2024.utils.database.configuration;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfigurationProvider implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getUrl() {
        return environment.getProperty("spring.datasource.url");
    }

    public String getUsername() {
        return environment.getProperty("spring.datasource.username");
    }

    public String getPassword() {
        return environment.getProperty("spring.datasource.password");
    }
}

