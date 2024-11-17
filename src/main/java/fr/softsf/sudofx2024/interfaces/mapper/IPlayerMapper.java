package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.PlayerDto;
import fr.softsf.sudofx2024.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link Player} to objects of type {@link PlayerDto} and vice versa.
 * It uses MapStruct to automatically generate the implementations
 * of these mapping methods.
 */
@Mapper
public interface IPlayerMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    IPlayerMapper INSTANCE = Mappers.getMapper(IPlayerMapper.class);

    /**
     * Maps a Player object to a PlayerDto object.
     *
     * @param player the Player object to be mapped.
     * @return a PlayerDto object representing the data of the
     * provided Player object.
     */
    PlayerDto mapPlayerToDto(Player player);

    /**
     * Maps a PlayerDto object to a Player object.
     *
     * @param dto the PlayerDto object to be mapped.
     * @return a Player object representing the data of the
     * provided PlayerDto object.
     */
    Player mapPlayerDtoToPlayer(PlayerDto dto);
}

