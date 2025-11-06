package mesmo.eu.relacionamento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import mesmo.eu.relacionamento.dto.PedidoDto;
import mesmo.eu.relacionamento.entity.Pedido;

@Mapper(componentModel="spring")
public interface PedidoMapper
{
    @Mapping(target="id", ignore=true)
    Pedido toEntity(PedidoDto dto);
}
