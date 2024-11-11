package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record SoftwareDto(
        Long softwareid,
        @Size(max = 128)
        String currentversion,
        @Size(max = 128)
        String lastversion,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
}
