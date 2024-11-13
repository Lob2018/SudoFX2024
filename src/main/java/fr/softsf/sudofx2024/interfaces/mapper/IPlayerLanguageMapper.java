package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.PlayerLanguageDto;
import fr.softsf.sudofx2024.model.PlayerLanguage;
import org.mapstruct.factory.Mappers;

public interface IPlayerLanguageMapper {
    IPlayerLanguageMapper INSTANCE = Mappers.getMapper(IPlayerLanguageMapper.class);

    PlayerLanguageDto mapPlayerLanguageToDto(PlayerLanguage playerLanguage);

    PlayerLanguage mapPlayerLanguageDtoToPlayerLanguage(PlayerLanguageDto dto);
}
