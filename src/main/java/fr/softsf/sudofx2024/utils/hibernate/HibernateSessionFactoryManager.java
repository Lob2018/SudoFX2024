package fr.softsf.sudofx2024.utils.hibernate;

import fr.softsf.sudofx2024.interfaces.IHibernateConfiguration;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import fr.softsf.sudofx2024.utils.os.OsInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

public class HibernateSessionFactoryManager {
    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateSessionFactoryManager.class);
    private static IHibernateConfiguration iDefaultHibernateConfiguration;

    public static synchronized SessionFactory getSessionFactory() {
        if (iDefaultHibernateConfiguration == null) {
            iDefaultHibernateConfiguration = new H2SessionFactoryConfigurator(new OsDynamicFolders(OsInfo.OS_NAME).getIOsFoldersFactory());
        }
        return getFactory(iDefaultHibernateConfiguration);
    }

    public static synchronized SessionFactory getSessionFactory(IHibernateConfiguration iHibernateConfigurationP) {
        iDefaultHibernateConfiguration = iHibernateConfigurationP;
        return getFactory(iHibernateConfigurationP);
    }

    private static SessionFactory getFactory(IHibernateConfiguration iHibernateConfigurationP) {
        if (sessionFactory == null) {
            try {
                sessionFactory = iHibernateConfigurationP.getSessionFactory();
            } catch (Exception e) {
                logger.error("Failed to create Hibernate SessionFactory", e);
                throw new ExceptionInInitializerError("Failed to create Hibernate SessionFactory: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static synchronized void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
