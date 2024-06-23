package fr.softsf.sudofx2024.repository;

import fr.softsf.sudofx2024.model.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
}
