package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.ProdutoDtoSemItens;
import mesmo.eu.relacionamento.dto.relatorios.Exclusoes;
import mesmo.eu.relacionamento.dto.relatorios.ReceitaPorCategoria;
import mesmo.eu.relacionamento.dto.relatorios.UsoIngredientes;
import mesmo.eu.relacionamento.service.RelatorioService;

@RestController
public class RelatorioController
{
    @Autowired
    private RelatorioService relatorioService;
    
    @GetMapping("/rel/consumo-ingredientes")
    public List<UsoIngredientes> listarConsumoIngredientes()
    {
        return relatorioService.listarConsumoIngredientes();
    }

    @GetMapping("/rel/categorias")
    public List<ReceitaPorCategoria> receitaPorCategoria()
    {
        return relatorioService.receitaPorCategoria();
    }

    @GetMapping("/rel/uso-ingrediente")
    public List<ProdutoDtoSemItens> produtosPorIngrediente(
        @RequestParam(required=true) String ingrediente)
    {
        return relatorioService.listarProdutosPorIngrediente(ingrediente);
    }

    @GetMapping("/rel/exclusoes")
    public List<Exclusoes> buscarExclusoes()
    {
        return relatorioService.listarExcluoes();
    }
}