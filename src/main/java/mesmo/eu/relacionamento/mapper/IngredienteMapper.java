package mesmo.eu.relacionamento.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import mesmo.eu.relacionamento.dto.IngredienteDto;
import mesmo.eu.relacionamento.entity.Ingrediente;

@Mapper(componentModel="spring")
public interface IngredienteMapper
{
    IngredienteDto toDto(Ingrediente ingrediente);

    @Mapping(target="id", ignore=true)
    Ingrediente toEntity(IngredienteDto dto);

    @Mapping(target="id", ignore=true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(IngredienteDto dto,
        @MappingTarget Ingrediente ingrediente);
}
