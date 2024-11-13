package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.GameDto;
import fr.softsf.sudofx2024.model.Game;
import org.mapstruct.factory.Mappers;

public interface IGameMapper {
    IGameMapper INSTANCE = Mappers.getMapper(IGameMapper.class);

    GameDto mapGameToDto(Game game);

    Game mapGameDtoToGame(GameDto dto);
}
