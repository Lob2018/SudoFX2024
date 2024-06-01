package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "software")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID softwareuuid;

    @Size(max = 128)
    private String currentversion;

    @Size(max = 128)
    private String lastversion;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}


