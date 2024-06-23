package fr.softsf.sudofx2024.repository;

import fr.softsf.sudofx2024.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {
}
