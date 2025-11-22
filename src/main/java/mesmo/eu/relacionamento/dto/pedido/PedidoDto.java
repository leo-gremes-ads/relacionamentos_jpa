package mesmo.eu.relacionamento.dto.pedido;

import java.time.LocalDate;
import java.util.List;

import mesmo.eu.relacionamento.dto.ItemDto;

public record PedidoDto
(
    Long id,
    LocalDate data,
    Integer numeroMesa,
    List<ItemDto> itens
)
{}
