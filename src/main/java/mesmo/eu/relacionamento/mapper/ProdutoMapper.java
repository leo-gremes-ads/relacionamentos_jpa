package mesmo.eu.relacionamento.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import mesmo.eu.relacionamento.dto.IngredienteProdutoDto;
import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.dto.ProdutoDtoSemItens;
import mesmo.eu.relacionamento.entity.IngredienteProduto;
import mesmo.eu.relacionamento.entity.Produto;

@Mapper(componentModel="spring")
public interface ProdutoMapper
{
    ProdutoDto toDto(Produto produto);

    ProdutoDtoSemItens toDtoSemItens(Produto produto);
    
    @Mapping(target="ingredientes", ignore=true)
    @Mapping(target="id", ignore=true)
    Produto toEntity(ProdutoDto dto);

    @Mapping(target="ingredienteId", source="ingrediente.id")
    @Mapping(target="nome", source="ingrediente.nome")
    @Mapping(target="unDeMedida", source="ingrediente.unDeMedida")
    IngredienteProdutoDto toDto(IngredienteProduto ingredienteProduto);

    @Mapping(target="id", ignore=true)
    @Mapping(target="produto", ignore=true)
    @Mapping(target="ingrediente", ignore=true)
    IngredienteProduto toEntity(IngredienteProdutoDto dto);

    @Mapping(target="id", ignore=true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ProdutoDto dto,
        @MappingTarget Produto produto);

    @Mapping(target="id", ignore=true)
    @Mapping(target="produto", ignore=true)
    @Mapping(target="ingrediente", ignore=true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(IngredienteProdutoDto dto, @MappingTarget IngredienteProduto ip);
}