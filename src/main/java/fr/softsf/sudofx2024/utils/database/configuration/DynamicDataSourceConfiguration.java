package fr.softsf.sudofx2024.utils.database.configuration;

import fr.softsf.sudofx2024.utils.database.DatabaseMigration;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    public DataSource dynamicDataSource(@Autowired WindowsFolderFactory osFolderFactory, @Autowired ApplicationKeystore keystore) {
        keystore.setupApplicationKeystore();
        DatabaseMigration.configure(keystore, osFolderFactory);
        return DataSourceBuilder.create()
                .driverClassName("org.hsqldb.jdbcDriver")
                .url("jdbc:hsqldb:file:" + osFolderFactory.getOsDataFolderPath() + "/sudofx2024db;shutdown=true")
                .username(keystore.getUsername())
                .password(keystore.getPassword())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dynamicDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dynamicDataSource);
        entityManagerFactory.setPackagesToScan("fr.softsf.sudofx2024.model");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.getJpaPropertyMap().put("hibernate.format_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.use_sql_comments", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.show_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
        return entityManagerFactory;
    }
}
