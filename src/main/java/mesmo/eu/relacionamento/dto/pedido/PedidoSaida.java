package mesmo.eu.relacionamento.dto.pedido;

import java.time.LocalDate;

public record PedidoSaida
(
    Long id,
    LocalDate data,
    String cliente,
    Integer qtdItens,
    Double valorTotal
) {}