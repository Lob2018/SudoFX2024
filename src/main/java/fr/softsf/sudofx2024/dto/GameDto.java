package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GameDto(
        Long gameid,
        GridDto grididDto,
        PlayerDto playeridDto,
        GameLevelDto levelidDto,
        boolean isselected,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
}
