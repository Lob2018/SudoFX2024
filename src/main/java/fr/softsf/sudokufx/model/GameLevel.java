package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private Byte levelid;

    @NotNull
    @Setter
    @Min(1)
    @Max(3)
    private Byte level;
}
