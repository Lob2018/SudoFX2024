package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.SoftwareDto;
import fr.softsf.sudofx2024.model.Software;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ISoftwareMapper {
    ISoftwareMapper INSTANCE = Mappers.getMapper(ISoftwareMapper.class);

    SoftwareDto mapSoftwareToDto(Software software);

    Software mapSoftwareDtoToSoftware(SoftwareDto dto);
}
