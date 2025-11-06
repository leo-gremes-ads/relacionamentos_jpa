package mesmo.eu.relacionamento.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mesmo.eu.relacionamento.dto.ItemDto;
import mesmo.eu.relacionamento.dto.PedidoDto;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.Pedido;

@Mapper(componentModel="spring")
public interface PedidoMapper
{
    @Mapping(target="itens", source="itens")
    PedidoDto toDto(Pedido pedido);

    @InheritInverseConfiguration
    Pedido toEntity(PedidoDto dto);

    @Mapping(target="seq", source="id.seq")
    @Mapping(target="produtoId", source="produto.id")
    ItemDto toDto(Item item);

    @Mapping(target="id.seq", source="seq")
    @Mapping(target="pedido", ignore=true)
    @Mapping(target="produto", ignore=true)
    Item toEntity(ItemDto dto);
}
