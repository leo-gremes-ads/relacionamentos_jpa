package mesmo.eu.relacionamento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mesmo.eu.relacionamento.dto.IngredienteDto;
import mesmo.eu.relacionamento.entity.Ingrediente;

@Mapper(componentModel="spring")
public interface IngredienteMapper
{
    IngredienteDto toDto(Ingrediente ingrediente);

    @Mapping(target="id", ignore=true)
    Ingrediente toEntity(IngredienteDto dto);
}
