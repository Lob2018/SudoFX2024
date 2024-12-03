package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;

public record MenuDto(
        Long menuid,
        @NotNull
        int mode
) {
}
