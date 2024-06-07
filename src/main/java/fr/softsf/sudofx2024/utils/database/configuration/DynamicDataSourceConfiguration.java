package fr.softsf.sudofx2024.utils.database.configuration;

import fr.softsf.sudofx2024.utils.MyLogback;
import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    int logbackInitialization(@Autowired MyLogback myLogback) {
        myLogback.printLogEntryMessage();
        return 0;
    }

    @DependsOn({"logbackInitialization"})
    @Bean
    int databaseMigration(@Autowired OsDynamicFolders osFolderFactory, @Autowired ApplicationKeystore keystore) {
        keystore.setupApplicationKeystore();
        DatabaseMigration.configure(keystore, osFolderFactory.osFolderFactory());
        return 0;
    }

    @DependsOn({"databaseMigration"})
    @Bean
    public DataSource dataSourceInitialization(@Autowired OsDynamicFolders osFolderFactory, @Autowired ApplicationKeystore keystore) {
        return DataSourceBuilder.create()
                .driverClassName("org.hsqldb.jdbcDriver")
                .url("jdbc:hsqldb:file:" + osFolderFactory.osFolderFactory().getOsDataFolderPath() + "/sudofx2024db;shutdown=true")
                .username(keystore.getUsername())
                .password(keystore.getPassword())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSourceInitialization) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSourceInitialization);
        entityManagerFactory.setPackagesToScan("fr.softsf.sudofx2024.model");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.getJpaPropertyMap().put("hibernate.format_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.use_sql_comments", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.show_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
        return entityManagerFactory;
    }
}
