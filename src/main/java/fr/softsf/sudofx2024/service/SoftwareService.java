package fr.softsf.sudofx2024.service;

import fr.softsf.sudofx2024.model.SoftwareModel;
import fr.softsf.sudofx2024.utils.hibernate.HibernateSessionFactoryManager;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SoftwareService {
    private static final Logger logger = LogManager.getLogger(HibernateSessionFactoryManager.class);


    public Optional<SoftwareModel> createSoftware() {
        try (Session session = HibernateSessionFactoryManager.getSessionFactory().openSession()) {
            SoftwareModel software = SoftwareModel.builder()
                    .currentversion("1.0.3")
                    .lastversion("1.0.3")
                    .createdat(LocalDateTime.now())
                    .updatedat(LocalDateTime.now())
                    .build();
            session.beginTransaction();
            session.save(software);
            session.getTransaction().commit();
            return Optional.of(software);
        } catch (Exception e) {
            logger.error("Error creating software", e);
            return Optional.empty();
        }
    }

    public Optional<SoftwareModel> getSoftware() {

        String nativeQuery = "SELECT * FROM software ORDER BY createdat DESC LIMIT 1;";
        try (Session session = HibernateSessionFactoryManager.getSessionFactory().openSession()) {
            Query query = session.createNativeQuery(nativeQuery, SoftwareModel.class);
            SoftwareModel software = (SoftwareModel) query.uniqueResult();
            return Optional.of(software); // Return Optional.of if successful
        } catch (NoResultException e) {
            logger.debug("No software found", e); // Log at debug level (optional)
            return Optional.empty();  // Return Optional.empty() on exception
        } catch (Exception e) {
            logger.error("Exception retrieving software", e);
            return Optional.empty();  // Return Optional.empty() on other exceptions
        }
    }
}
