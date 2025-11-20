package mesmo.eu.relacionamento.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IngredienteProduto
{
    @EmbeddedId
    private IngredienteProdutoId id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ProdutoId")
    @JoinColumn(name="produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredienteId")
    @JoinColumn(name="ingrediente_id")
    private Ingrediente ingrediente;

    private Float qtd;
}
