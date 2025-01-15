package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "gamelevel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GameLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelid;

    @NotNull
    @Setter
    @Size(max = 64)
    private String levelname;
}
