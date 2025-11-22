package mesmo.eu.relacionamento.dto.relatorios;

public record ReceitaPorCategoria
(
    String categoria,
    Double receita,
    Double porcentagem
) {}