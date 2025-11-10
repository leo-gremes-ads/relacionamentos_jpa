package mesmo.eu.relacionamento.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mesmo.eu.relacionamento.dto.ItemDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoDto;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.Pedido;

@Mapper(componentModel="spring")
public interface PedidoMapper
{
    @Mapping(target="itens", source="itens")
    PedidoDto toDto(Pedido pedido);

    @InheritInverseConfiguration
    @Mapping(target="data", ignore=true)
    Pedido toEntity(PedidoDto dto);

    @Mapping(target="seq", source="id.seq")
    @Mapping(target="produtoId", source="produto.id")
    @Mapping(target="nome", source="produto.nome")
    ItemDto toDto(Item item);

    @Mapping(target="id", ignore=true)
    @Mapping(target="pedido", ignore=true)
    @Mapping(target="produto", ignore=true)
    Item toEntity(ItemDto dto);
}
