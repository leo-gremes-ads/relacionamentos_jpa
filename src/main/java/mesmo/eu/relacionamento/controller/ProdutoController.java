package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.IngredienteProdutoDto;
import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.service.ProdutoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produto")
public class ProdutoController
{
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDto> novoProduto(@RequestBody ProdutoDto dto)
    {
        ProdutoDto produtoDto = produtoService.salvar(dto);
        return ResponseEntity.ok(produtoDto);
    }

    @PostMapping("/{id}/ing")
    public ProdutoDto adicionarIngrediente(@PathVariable("id") Long id,
        @RequestBody IngredienteProdutoDto dto)
    {
        return produtoService.adicionarIngrediente(id, dto);
    }

    @GetMapping
    public List<ProdutoDto> listarTodos()
    {
        return produtoService.procurarTodos();
    }

    @GetMapping("/{id}")
    public ProdutoDto procurarPorId(@PathVariable("id") Long id)
    {
        return produtoService.procurarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable("id") Long id)
    {
        produtoService.deletarPorId(id);
    }

    @DeleteMapping("/{produtoId}/ing/{ingredienteId}")
    public ProdutoDto removerIngrediente(@PathVariable("produtoId") Long produtoId,
        @PathVariable("ingredienteId") Long ingredienteId)
    {
        return produtoService.removerIngrediente(produtoId, ingredienteId);
    }

    @PutMapping("/{id}")
    public ProdutoDto atualizarTotal(@PathVariable("id") Long id,
        @RequestBody ProdutoDto dto)
    {
        return produtoService.atualizarTotal(id, dto);
    }

    @PutMapping("/{produtoId}/ing/{ingredienteId}")
    public ProdutoDto substituirIngrediente(@PathVariable("produtoId") Long produtoId,
        @PathVariable("ingredienteId") Long ingredienteId, @RequestBody IngredienteProdutoDto dto)
    {
        return produtoService.substituirIngrediente(produtoId, ingredienteId, dto);
    }

    @PatchMapping("{id}")
    public ProdutoDto atualizarParcial(@PathVariable("id") Long id,
        @RequestBody ProdutoDto dto)
    {
        return produtoService.atualizarParcial(id, dto);
    }


    @PatchMapping("/{produtoId}/ing/{ingredienteId}")
    public ProdutoDto atualizarIngrediente(@PathVariable("produtoId") Long produtoId,
        @PathVariable("ingredienteId") Long ingredienteId, @RequestBody IngredienteProdutoDto dto)
    {
        return produtoService.atualizarIngrediente(produtoId, ingredienteId, dto);
    }
}