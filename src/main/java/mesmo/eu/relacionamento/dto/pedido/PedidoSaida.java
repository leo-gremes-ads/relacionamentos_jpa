package mesmo.eu.relacionamento.dto.pedido;

import java.time.LocalDate;

public record PedidoSaida
(
    Long id,
    LocalDate data,
    Integer numeroMesa,
    Integer qtdItens,
    Double valorTotal
) {}