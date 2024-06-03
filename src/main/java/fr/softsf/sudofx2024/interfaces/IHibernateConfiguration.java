package fr.softsf.sudofx2024.interfaces;

import org.hibernate.SessionFactory;

public interface IHibernateConfiguration {
    SessionFactory getSessionFactory();
}
