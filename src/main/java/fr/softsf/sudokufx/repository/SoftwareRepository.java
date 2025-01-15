package fr.softsf.sudokufx.repository;

import fr.softsf.sudokufx.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {
    @Query(value = "SELECT * FROM software LIMIT 1", nativeQuery = true)
    Optional<Software> findFirstSoftware();
}
