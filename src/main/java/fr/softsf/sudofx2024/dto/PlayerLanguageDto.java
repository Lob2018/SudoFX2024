package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerLanguageDto(
        Long playerlanguageid,
        @NotNull
        @Size(max = 2)
        String iso
) {
}
