package fr.softsf.sudofx2024.repository;

import fr.softsf.sudofx2024.model.Background;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Long> {
}
