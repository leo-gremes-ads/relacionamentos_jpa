package mesmo.eu.relacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mesmo.eu.relacionamento.dto.ProdutoDtoSemItens;
import mesmo.eu.relacionamento.dto.relatorios.Exclusoes;
import mesmo.eu.relacionamento.dto.relatorios.ReceitaPorCategoria;
import mesmo.eu.relacionamento.dto.relatorios.UsoIngredientes;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.mapper.ProdutoMapper;
import mesmo.eu.relacionamento.repository.RelatoriosRepository;

@Service
public class RelatorioService
{
    @Autowired
    private RelatoriosRepository relatoriosRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<UsoIngredientes> listarConsumoIngredientes()
    {
        return relatoriosRepository.listarConsumoIngredientes();
    }   
    
    public List<ReceitaPorCategoria> receitaPorCategoria()
    {
        return relatoriosRepository.receitaPorCategoria();
    }

    public List<ProdutoDtoSemItens> listarProdutosPorIngrediente(String ingrediente)
    {
        List<Produto> lista = relatoriosRepository.produtosPorIngrediente(ingrediente);
        return lista.stream()
            .map(p -> produtoMapper.toDtoSemItens(p)).toList();
    }

    public List<Exclusoes> listarExcluoes()
    {
        return relatoriosRepository.buscarExclusoes();
    }
}
