package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.GameDto;
import fr.softsf.sudofx2024.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link Game} to objects of type {@link GameDto} and vice versa.
 * It uses MapStruct to automatically generate the implementations of
 * these mapping methods.
 */
@Mapper
public interface IGameMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    IGameMapper INSTANCE = Mappers.getMapper(IGameMapper.class);

    /**
     * Maps a Game object to a GameDto object.
     *
     * @param game the Game object to be mapped.
     * @return a GameDto object representing the data of the
     * provided Game object.
     */
    GameDto mapGameToDto(Game game);

    /**
     * Maps a GameDto object to a Game object.
     *
     * @param dto the GameDto object to be mapped.
     * @return a Game object representing the data of the
     * provided GameDto object.
     */
    Game mapGameDtoToGame(GameDto dto);
}
