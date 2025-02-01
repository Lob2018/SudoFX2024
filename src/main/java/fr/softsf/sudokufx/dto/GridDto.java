package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GridDto(
        Long gridid,
        @NotNull
        @Size(max = 81)
        String defaultgridvalue,
        @NotNull
        @Size(max = 810)
        String gridvalue,
        @NotNull
        @Min(0)
        @Max(100)
        Byte difficulty
) {
}
