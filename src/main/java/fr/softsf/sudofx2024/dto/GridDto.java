package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.Size;

public record GridDto(
        Long gridid,
        @Size(max = 162)
        String defaultgridvalue,
        @Size(max = 162)
        String gridvalue
) {
}
