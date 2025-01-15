package fr.softsf.sudokufx.interfaces.mapper;

import fr.softsf.sudokufx.model.PlayerLanguage;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link PlayerLanguage} to objects of type {@link PlayerLanguageDto}
 * and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper
public interface IPlayerLanguageMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    IPlayerLanguageMapper INSTANCE = Mappers.getMapper(IPlayerLanguageMapper.class);

    /**
     * Maps a PlayerLanguage object to a PlayerLanguageDto object.
     *
     * @param playerLanguage the PlayerLanguage object to be mapped.
     * @return a PlayerLanguageDto object representing the data of the
     * provided PlayerLanguage object.
     */
    PlayerLanguageDto mapPlayerLanguageToDto(PlayerLanguage playerLanguage);

    /**
     * Maps a PlayerLanguageDto object to a PlayerLanguage object.
     *
     * @param dto the PlayerLanguageDto object to be mapped.
     * @return a PlayerLanguage object representing the data of the
     * provided PlayerLanguageDto object.
     */
    PlayerLanguage mapPlayerLanguageDtoToPlayerLanguage(PlayerLanguageDto dto);
}

