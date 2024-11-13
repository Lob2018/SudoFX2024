package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GameDto(
        Long gameid,
        @NotNull
        GridDto grididDto,
        @NotNull
        PlayerDto playeridDto,
        @NotNull
        GameLevelDto levelidDto,
        @NotNull
        boolean isselected,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
}
