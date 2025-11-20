package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.IngredienteDto;
import mesmo.eu.relacionamento.service.IngredienteService;

@RestController
@RequestMapping("/ingrediente")
public class IngredienteController
{
    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public List<IngredienteDto> listarTodos()
    {
        return ingredienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public IngredienteDto listarUm(@PathVariable("id") Long id)
    {
        return ingredienteService.listarUm(id);
    }

    @PostMapping
    public IngredienteDto salvar(@RequestBody IngredienteDto dto)
    {
        return ingredienteService.salvar(dto);
    }

    @DeleteMapping("/{id}")
    public String apagarPorId(@PathVariable("id") Long id)
    {
        ingredienteService.apagarPorId(id);
        return ("Ingrediente de id " + id + " apagado");
    }

    @PutMapping("/{id}")
    public IngredienteDto atualizar(@PathVariable("id") Long id,
        @RequestBody IngredienteDto dto)
    {
        return ingredienteService.atualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public IngredienteDto atualizarParcual(@PathVariable("id") Long id,
        @RequestBody IngredienteDto dto)
    {
        return ingredienteService.atualizarParcial(id, dto);
    }
}
