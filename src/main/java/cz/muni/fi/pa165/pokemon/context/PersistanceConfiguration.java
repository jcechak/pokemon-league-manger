package cz.muni.fi.pa165.pokemon.context;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class cofigures all the beans necessary to provide persistance context
 *
 * @author Jaroslav Cechak
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"cz.muni.fi.pa165.pokemon.dao"})
public class PersistanceConfiguration {

    /**
     * Creates {@link javax.sql.DataSource DataSource} object for an embedded
     * database (DERBY)
     *
     * @return datasource for an embedded database
     */
    @Bean
    public DataSource embeddedDataSource() {
        EmbeddedDatabase edb = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY).build();
        return edb;
    }

    /**
     * Defines hibernate settings in a form of a
     * {@link java.util.Properties Properties} object.
     *
     * @return hibernate settings
     */
    private Properties hibernateProperties() {
        // Hibernate settings
        Properties properties = new Properties();
        // set tables creation policy
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        // show SQL queries in log
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }

    /**
     * Creates entity manager factory so that it can be injected and used to
     * create entity managers in DAO classes
     *
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // create entity manager factory
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        // set datasource to embedded database
        emf.setDataSource(embeddedDataSource());

        // create and set persistance vendor as Hibernate 
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        // configure hibernate properties
        emf.setJpaProperties(hibernateProperties());

        return emf;
    }
}