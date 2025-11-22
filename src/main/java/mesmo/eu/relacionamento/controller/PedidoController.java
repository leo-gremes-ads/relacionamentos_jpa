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

import mesmo.eu.relacionamento.dto.ItemDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoSaida;
import mesmo.eu.relacionamento.service.PedidoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pedido")
public class PedidoController
{
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDto> novoPedido(@RequestBody PedidoDto dto)
    {
        PedidoDto pedido = pedidoService.salvar(dto);
        return ResponseEntity.ok(pedido);        
    }

    @PostMapping("/{id}/itens")
    public PedidoDto adicionarItem(@PathVariable("id") Long id,
        @RequestBody ItemDto dto)
    {
        return pedidoService.adicionarItem(id, dto);
    }

    @GetMapping
    public List<PedidoSaida> listarTodos()
    {
        return pedidoService.procurarTodos();
    }

    @GetMapping("/{id}")
    public PedidoDto listarPorId(@PathVariable("id") Long id)
    {
        return pedidoService.procurarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable("id") Long id)
    {
        pedidoService.deletarPorId(id);
    }

    @DeleteMapping("/{id}/itens/{seq}")
    public PedidoDto removerItem(@PathVariable("id") Long id,
        @PathVariable("seq") Integer seq)
    {
        return pedidoService.removerItem(id, seq);
    }

    @PutMapping("/{id}")
    public PedidoDto atualizarTotal(@PathVariable("id") Long id,
        @RequestBody PedidoDto dto)
    {
        return pedidoService.atualizarTotal(id, dto);
    }

    @PutMapping("/{id}/itens/{seq}")
    public PedidoDto substituirItem(@PathVariable("id") Long id,
        @PathVariable("seq") Integer seq, @RequestBody ItemDto dto)
    {
        return pedidoService.substituirItem(id, seq, dto);
    }

    @PatchMapping("{id}")
    public PedidoDto atualizarParcial(@PathVariable("id") Long id,
        @RequestBody PedidoDto dto)
    {
        return pedidoService.atualizarParcial(id, dto);
    }

    @PatchMapping("/{id}/itens/{seq}")
    public PedidoDto atualizarItem(@PathVariable("id") Long id,
        @PathVariable("seq") Integer seq, @RequestBody ItemDto dto)
    {
        return pedidoService.atualizarItem(id, seq, dto);
    }
}