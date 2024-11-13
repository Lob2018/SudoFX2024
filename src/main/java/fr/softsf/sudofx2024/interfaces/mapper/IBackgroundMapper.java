package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.BackgroundDto;
import fr.softsf.sudofx2024.model.Background;
import org.mapstruct.factory.Mappers;

public interface IBackgroundMapper {
    IBackgroundMapper INSTANCE = Mappers.getMapper(IBackgroundMapper.class);

    BackgroundDto mapBackgroundToDto(Background background);

    Background mapBackgroundDtoToBackground(BackgroundDto dto);
}
