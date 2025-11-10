package mesmo.eu.relacionamento.dto.pedido;

import java.util.List;

import mesmo.eu.relacionamento.dto.ItemDto;

public record PedidoDto
(
    Long id,
    String cliente,
    List<ItemDto> itens
)
{}
