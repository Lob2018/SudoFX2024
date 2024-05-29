package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudofx2024.utils.os.WindowsFolderFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import static fr.softsf.sudofx2024.utils.MyEnums.LogBackTxt.EMPTY_HIBERNATE_CONFIGURATION_EXCEPTION;

@Slf4j
public final class HibernateSessionFactoryManager {
    private static SessionFactory sessionFactory;
    private static IHibernateConfiguration iDefaultHibernateConfiguration;

    @ExcludedFromCoverageReportGenerated
    private HibernateSessionFactoryManager() {
    }

    /**
     * Get the current Hibernate SessionFactory configured for HSQLDB
     *
     * @return The current Hibernate SessionFactory configured for HSQLDB
     */
    public static SessionFactory getSessionFactory() {
        return getSessionFactory(iDefaultHibernateConfiguration);
    }

    /**
     * Firstly called to set the IHibernateConfiguration
     * Return or get and set locally the Hibernate corresponding SessionFactory configured for HSQLDB
     *
     * @param iHibernateConfiguration The Hibernate SessionFactory configured for HSQLDB
     * @return The current Hibernate SessionFactory configured for HSQLDB
     */
    public static SessionFactory getSessionFactory(final IHibernateConfiguration iHibernateConfiguration) {
        if (iDefaultHibernateConfiguration == null) {
            log.info("\n▓▓ HibernateSessionFactoryManager getSessionFactory > setting IHibernateConfiguration");
            iDefaultHibernateConfiguration = iHibernateConfiguration;
        }
        if (sessionFactory == null) {
            try {
                sessionFactory = iHibernateConfiguration.getHibernateSessionFactory();
            } catch (Exception e) {
                log.error(String.format("██ Failed to create Hibernate SessionFactory (important : only one connection to the database is allowed) : %s", e.getMessage()), e);
                throw new ExceptionInInitializerError(String.format("██ Failed to create Hibernate SessionFactory: %s", e.getMessage()));
            }
        }
        return sessionFactory;
    }

    /**
     * Close the current Hibernate SessionFactory
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
            sessionFactory = null;
        }
    }



    public interface IHibernateConfiguration {
        /**
         * Get the current Hibernate SessionFactory configured for HSQLDB
         *
         * @return The current Hibernate SessionFactory configured for HSQLDB
         */
        SessionFactory getHibernateSessionFactory();
    }
}
