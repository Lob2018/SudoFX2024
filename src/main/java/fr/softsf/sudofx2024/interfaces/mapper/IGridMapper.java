package fr.softsf.sudofx2024.interfaces.mapper;

import fr.softsf.sudofx2024.dto.GridDto;
import fr.softsf.sudofx2024.model.Grid;
import org.mapstruct.factory.Mappers;

public interface IGridMapper {
    IGridMapper INSTANCE = Mappers.getMapper(IGridMapper.class);

    GridDto mapGridToDto(Grid grid);

    Grid mapGridDtoToGrid(GridDto dto);
}
