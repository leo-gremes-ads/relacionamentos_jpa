package mesmo.eu.relacionamento.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.relatorios.Exclusoes;
import mesmo.eu.relacionamento.dto.relatorios.ReceitaPorCategoria;
import mesmo.eu.relacionamento.dto.relatorios.UsoIngredientes;
import mesmo.eu.relacionamento.entity.Produto;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class RelatoriosRepository
{
    @PersistenceContext
    private EntityManager em;

    public List<UsoIngredientes> listarConsumoIngredientes()
    {
        String sql = """
            SELECT i.nome AS ingrediente,
                   SUM(ip.qtd * it.qtd) AS total,
                   i.un_de_medida
            FROM item it
            INNER JOIN produto p
            ON it.produto_id = p.produto_id
            INNER JOIN ingrediente_produto ip
            ON p.produto_id = ip.produto_id
            INNER JOIN ingrediente i
            ON i.id = ip.ingrediente_id
            GROUP BY i.nome;
            """;
        List<Object[]> resultado = (List<Object[]>) em.createNativeQuery(sql).getResultList();

        return resultado.stream().map(linha -> new UsoIngredientes(
                (String) linha[0],
                linha[1] != null ? ((Number) linha[1]).doubleValue() : 0.0,
                (String) linha[2]
            ))
            .toList();
    }

    public List<ReceitaPorCategoria> receitaPorCategoria()
    {
        String sql = """
            WITH tabela AS (
                SELECT p.categoria,
                       p.preco * it.qtd AS receita
            FROM produto p
            INNER JOIN item it
            ON p.produto_id = it.produto_id)
            SELECT categoria,
                   SUM(receita) AS receita,
                   SUM(ROUND(receita / (SELECT SUM(receita) FROM tabela), 4) * 100) AS porcentagem
            FROM tabela
            GROUP BY categoria;
            """;
        List<Object[]> resultado = em.createNativeQuery(sql).getResultList();
        return resultado.stream().map(linha -> new ReceitaPorCategoria(
                (String) linha[0],
                linha[1] != null ? ((Number) linha[1]).doubleValue() : 0.0,
                linha[2] != null ? ((Number) linha[2]).doubleValue() : 0.0
            ))
            .toList();
    }


    public List<Produto> produtosPorIngrediente(String ingrediente)
    {
        StoredProcedureQuery sp = em.createStoredProcedureQuery(
            "listarProdutosPorIngrediente",
            Produto.class
        );
        sp.registerStoredProcedureParameter(
            "nome_ingrediente",
            String.class,
            ParameterMode.IN
        );
        sp.setParameter("nome_ingrediente", ingrediente);
        return sp.getResultList();
    }

    public List<Exclusoes> buscarExclusoes()
    {
        String sql = """
                SELECT tabela, registro_id, usuario, data_exclusao
                FROM log_deletes
                """;
        List<Object[]> resultado = em.createNativeQuery(sql).getResultList();
        return resultado.stream()
            .map(linha -> new Exclusoes(
                (String) linha[0],
                (String) linha[1],
                (String) linha[2],
                ((Timestamp) linha[3]).toLocalDateTime().toLocalDate()
            ))
            .toList();
    }
}
