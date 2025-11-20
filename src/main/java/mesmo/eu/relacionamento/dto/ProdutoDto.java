package mesmo.eu.relacionamento.dto;

import java.util.List;

public record ProdutoDto
(
    Long id,
    String nome,
    String categoria,
    Double preco,
    List<IngredienteProdutoDto> ingredientes
) {}