package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.MenuDto;
import fr.softsf.sudofx2024.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link Menu} to objects of type {@link MenuDto} and vice versa.
 * It uses MapStruct to automatically generate the implementations
 * of these mapping methods.
 */
@Mapper
public interface IMenuMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    IMenuMapper INSTANCE = Mappers.getMapper(IMenuMapper.class);

    /**
     * Maps a Menu object to a MenuDto object.
     *
     * @param menu the Menu object to be mapped.
     * @return a MenuDto object representing the data of the
     * provided Menu object.
     */
    MenuDto mapMenuToDto(Menu menu);

    /**
     * Maps a MenuDto object to a Menu object.
     *
     * @param dto the MenuDto object to be mapped.
     * @return a Menu object representing the data of the
     * provided MenuDto object.
     */
    Menu mapMenuDtoToMenu(MenuDto dto);
}

