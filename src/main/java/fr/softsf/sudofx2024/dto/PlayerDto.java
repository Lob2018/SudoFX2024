package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public record PlayerDto(
        Long playerid,
        PlayerLanguageDto playerlanguageidDto,
        BackgroundDto backgroundidDto,
        MenuDto menuidDto,
        Set<GameDto> gamesDto,
        @NotNull
        @Size(max = 256)
        String name,
        boolean isselected,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
    public PlayerDto {
        if (gamesDto == null) {
            gamesDto = new LinkedHashSet<>();
        }
    }
}
