package fr.softsf.sudokufx.repository;

import fr.softsf.sudokufx.model.PlayerLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerLanguageRepository extends JpaRepository<PlayerLanguage, Long> {
}
