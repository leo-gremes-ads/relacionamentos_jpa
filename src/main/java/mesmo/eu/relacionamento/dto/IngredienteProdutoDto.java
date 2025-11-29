package mesmo.eu.relacionamento.dto;

public record IngredienteProdutoDto
(
    //Long produtoId,
    Long ingredienteId,
    String nome,
    Float qtd,
    String unDeMedida
) {}