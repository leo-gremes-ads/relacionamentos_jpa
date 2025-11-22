package mesmo.eu.relacionamento.entity;

import java.time.LocalDate;
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
public class Pedido
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pedido_id")
    private Long id;

    private Integer numeroMesa;

    private LocalDate data;

    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Item> itens = new ArrayList<>();

    public void adicionarItem(Item item)
    {
        if (itens == null)
            itens = new ArrayList<>();
        item.setPedido(this);
        item.getId().setPedidoId(this.id);
        item.getId().setSeq(itens.size() + 1);
        itens.add(item);
    }
}