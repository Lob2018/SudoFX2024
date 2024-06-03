package fr.softsf.sudofx2024.service;

import fr.softsf.sudofx2024.model.SoftwareModel;
import fr.softsf.sudofx2024.utils.database.hibernate.HibernateSessionFactoryManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

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
            return Optional.of(software); // Return Optional.of if successful
        } catch (NoResultException e) {
            log.error(String.format("██ No software found : %s", e.getMessage()), e);// Log at debug level (optional)
            return Optional.empty();  // Return Optional.empty() on exception
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
