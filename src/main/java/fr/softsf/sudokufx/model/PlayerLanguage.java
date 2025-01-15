package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "playerlanguage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PlayerLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerlanguageid;

    @NotNull
    @Setter
    @Size(max = 2)
    private String iso;
}
