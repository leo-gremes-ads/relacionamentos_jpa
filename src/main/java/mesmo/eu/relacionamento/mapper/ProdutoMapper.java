package mesmo.eu.relacionamento.mapper;

import org.mapstruct.Mapper;

import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.entity.Produto;

@Mapper(componentModel="spring")
public interface ProdutoMapper
{
    ProdutoDto toDto(Produto produto);
    
    Produto toEntity(ProdutoDto dto);
}
