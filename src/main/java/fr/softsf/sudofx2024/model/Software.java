package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "software")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long softwareid;

    @Size(max = 128)
    private String currentversion;

    @Size(max = 128)
    private String lastversion;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}


