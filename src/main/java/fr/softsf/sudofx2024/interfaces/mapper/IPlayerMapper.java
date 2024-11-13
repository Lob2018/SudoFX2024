package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.PlayerDto;
import fr.softsf.sudofx2024.model.Player;
import org.mapstruct.factory.Mappers;

public interface IPlayerMapper {
    IPlayerMapper INSTANCE = Mappers.getMapper(IPlayerMapper.class);

    PlayerDto mapPlayerToDto(Player player);

    Player mapPlayerDtoToPlayer(PlayerDto dto);
}
