package fr.softsf.sudokufx.repository;

import fr.softsf.sudokufx.model.Grid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridRepository extends JpaRepository<Grid, Long> {
}
