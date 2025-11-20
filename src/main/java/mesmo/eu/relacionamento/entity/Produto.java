package mesmo.eu.relacionamento.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Produto
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="produto_id")
    private Long id;
    private String nome;
    private String categoria;
    private Double preco;

    @OneToMany(mappedBy="produto", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<IngredienteProduto> ingredientes = new ArrayList<>();
}
