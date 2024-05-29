package fr.softsf.sudofx2024.service;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.query.Query;

import fr.softsf.sudofx2024.model.SoftwareModel;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoftwareService {

    public Optional<SoftwareModel> createSoftware(SoftwareModel software) {
        try (Session session = HibernateSessionFactoryManager.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(software);
            session.getTransaction().commit();
            return Optional.of(software);
        } catch (Exception e) {
            log.error(String.format("██ Error creating software :  %s", e.getMessage()), e);
            return Optional.empty();
        }
    }

    public Optional<SoftwareModel> getSoftware() {
        String nativeQuery = "SELECT * FROM software ORDER BY createdat DESC LIMIT 1;";
        try (Session session = HibernateSessionFactoryManager.getSessionFactory().openSession()) {
            Query<SoftwareModel> query = session.createNativeQuery(nativeQuery, SoftwareModel.class);
            SoftwareModel software = query.uniqueResult();
            return Optional.of(software);
        } catch (NoResultException e) {
            log.error(String.format("██ No software found : %s", e.getMessage()), e);
            return Optional.empty();
        } catch (Exception e) {
            log.error(String.format("██ Exception retrieving software : %s", e.getMessage()), e);
            return Optional.empty();
        }
    }

    public Optional<SoftwareModel> updateSoftware(SoftwareModel software) {
        try (Session session = HibernateSessionFactoryManager.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(software);
            session.getTransaction().commit();
            return Optional.of(software);
        } catch (Exception e) {
            log.error(String.format("██ Error updating software : %s", e.getMessage()), e);
            return Optional.empty();
        }
    }
}
