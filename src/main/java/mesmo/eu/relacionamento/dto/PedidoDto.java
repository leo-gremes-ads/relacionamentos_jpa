package mesmo.eu.relacionamento.dto;

import java.util.List;

public record PedidoDto
(
    Long id,
    String cliente,
    List<ItemDto> itens
)
{}
