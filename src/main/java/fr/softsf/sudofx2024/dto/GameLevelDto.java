package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GameLevelDto(
        Long levelid,
        @NotNull
        @Size(max = 64)
        String levelname
) {
}
