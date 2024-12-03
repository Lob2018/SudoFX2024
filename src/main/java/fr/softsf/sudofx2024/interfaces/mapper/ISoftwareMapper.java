package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.SoftwareDto;
import fr.softsf.sudofx2024.model.Software;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * This interface defines methods for mapping objects of type
 * {@link Software} to objects of type {@link SoftwareDto} and vice versa.
 * It uses MapStruct to automatically generate the implementations
 * of these mapping methods.
 */
@Mapper
public interface ISoftwareMapper {
    /**
     * This instance is created by MapStruct and provides access to the
     * mapping methods defined in this interface.
     */
    ISoftwareMapper INSTANCE = Mappers.getMapper(ISoftwareMapper.class);

    /**
     * Maps a Software object to a SoftwareDto object.
     *
     * @param software the Software object to be mapped.
     * @return a SoftwareDto object representing the data of the
     * provided Software object.
     */
    SoftwareDto mapSoftwareToDto(Software software);

    /**
     * Maps a SoftwareDto object to a Software object.
     *
     * @param dto the SoftwareDto object to be mapped.
     * @return a Software object representing the data of the
     * provided SoftwareDto object.
     */
    Software mapSoftwareDtoToSoftware(SoftwareDto dto);
}

