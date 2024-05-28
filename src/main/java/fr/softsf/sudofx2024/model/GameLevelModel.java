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
@Table(name = "gamelevel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameLevelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID leveluuid;

    @NotNull
    @Size(max = 64)
    private String levelname;
}