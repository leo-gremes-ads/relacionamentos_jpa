package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.service.ProdutoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produto")
public class ProdutoController
{
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> novoProduto(@RequestBody ProdutoDto dto)
    {
        Produto produto = produtoService.salvar(dto);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public List<Produto> listarTodos()
    {
        return produtoService.procurarTodos();
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable("id") Long id)
    {
        produtoService.deletarPorId(id);
    }
}