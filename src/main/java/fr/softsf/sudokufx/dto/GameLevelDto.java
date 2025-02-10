package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GameLevelDto(
        Byte levelid,
        @NotNull
        @Min(1)
        @Max(3)
        Byte level
) {
}
