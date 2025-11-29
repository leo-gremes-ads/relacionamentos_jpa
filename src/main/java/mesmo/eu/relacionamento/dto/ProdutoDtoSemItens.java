package mesmo.eu.relacionamento.dto;

public record ProdutoDtoSemItens
(
    Long id,
    String nome,
    String categoria,
    Double preco
) {}