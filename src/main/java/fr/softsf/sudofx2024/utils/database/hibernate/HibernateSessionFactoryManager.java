package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public final class HibernateSessionFactoryManager {
    private static SessionFactory sessionFactory;
    private static IHibernateConfiguration iDefaultHibernateConfiguration;

    @ExcludedFromCoverageReportGenerated
    private HibernateSessionFactoryManager() {
    }

    public static SessionFactory getSessionFactory() {
        return getSessionFactory(iDefaultHibernateConfiguration);
    }

    public static SessionFactory getSessionFactoryInit(final IHibernateConfiguration iHibernateConfiguration) {
        log.info("\n▓▓ getSessionFactory(iHibernateConfiguration)");
        iDefaultHibernateConfiguration = iHibernateConfiguration;
        return getSessionFactory(iHibernateConfiguration);
    }

    static SessionFactory getSessionFactory(final IHibernateConfiguration iHibernateConfiguration) {
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

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
            sessionFactory = null;
        }
    }

    public interface IHibernateConfiguration {
        SessionFactory getHibernateSessionFactory();
    }
}
