package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.MenuDto;
import fr.softsf.sudofx2024.model.Menu;
import org.mapstruct.factory.Mappers;

public interface IMenuMapper {
    IMenuMapper INSTANCE = Mappers.getMapper(IMenuMapper.class);

    MenuDto mapMenuToDto(Menu menu);

    Menu mapMenuDtoToMenu(MenuDto dto);
}
