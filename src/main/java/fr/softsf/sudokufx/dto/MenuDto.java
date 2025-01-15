package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;

public record MenuDto(
        Long menuid,
        @NotNull
        int mode
) {
}
