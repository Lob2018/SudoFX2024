package fr.softsf.sudokufx.repository;

import fr.softsf.sudokufx.model.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
}
