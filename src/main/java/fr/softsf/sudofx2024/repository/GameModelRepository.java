package fr.softsf.sudofx2024.repository;

import fr.softsf.sudofx2024.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameModelRepository extends JpaRepository<GameModel, UUID> {
}
