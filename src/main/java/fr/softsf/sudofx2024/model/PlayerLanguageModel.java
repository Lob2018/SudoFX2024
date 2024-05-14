package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "playerlanguage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerLanguageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID playerlanguageuuid;

    @NotNull
    @Size(max = 2)
    private String iso;
}
