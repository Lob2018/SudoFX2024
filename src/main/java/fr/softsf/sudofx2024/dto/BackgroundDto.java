package fr.softsf.sudofx2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BackgroundDto(
        Long backgroundid,
        @NotNull
        @Size(max = 7)
        String hexcolor,
        @Size(max = 260)
        String imagepath,
        @NotNull
        boolean isimage
) {
}
