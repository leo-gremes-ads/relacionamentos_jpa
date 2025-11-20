package mesmo.eu.relacionamento.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of={"ingredienteId", "ProdutoId"})
public class IngredienteProdutoId
{
    private Long ProdutoId;
    private Long ingredienteId;
}