package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GridDto(
        Long gridid,
        @NotNull
        @Size(max = 162)
        String defaultgridvalue,
        @NotNull
        @Size(max = 162)
        String gridvalue
) {
}
