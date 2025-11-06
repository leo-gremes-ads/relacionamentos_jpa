package mesmo.eu.relacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedido
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pedido_id")
    private Long id;
    private String cliente;
    private String item;
}
