package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.GameLevelDto;
import fr.softsf.sudofx2024.model.GameLevel;
import org.mapstruct.factory.Mappers;

public interface IGameLevelMapper {
    IGameLevelMapper INSTANCE = Mappers.getMapper(IGameLevelMapper.class);

    GameLevelDto mapGameLevelToDto(GameLevel gameLevel);

    GameLevel mapGameLevelDtoToGameLevel(GameLevelDto dto);
}
