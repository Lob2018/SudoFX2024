package fr.softsf.sudokufx.interfaces.mapper;

import fr.softsf.sudokufx.model.GameLevel;
import fr.softsf.sudokufx.dto.GameLevelDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link GameLevel} to objects of type {@link GameLevelDto} and vice versa.
 * It uses MapStruct to automatically generate the implementations of
 * these mapping methods.
 */
@Mapper
public interface IGameLevelMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    IGameLevelMapper INSTANCE = Mappers.getMapper(IGameLevelMapper.class);

    /**
     * Maps a GameLevel object to a GameLevelDto object.
     *
     * @param gameLevel the Background object to be mapped.
     * @return a GameLevelDto object representing the data of the
     * provided GameLevel object.
     */
    GameLevelDto mapGameLevelToDto(GameLevel gameLevel);

    /**
     * Maps a GameLevelDto object to a GameLevel object.
     *
     * @param dto the GameLevelDto object to be mapped.
     * @return a GameLevel object representing the data of the
     * provided GameLevelDto object.
     */
    GameLevel mapGameLevelDtoToGameLevel(GameLevelDto dto);
}