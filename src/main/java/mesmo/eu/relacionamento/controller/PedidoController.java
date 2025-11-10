package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.pedido.PedidoDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoSaida;
import mesmo.eu.relacionamento.service.PedidoService;

@RestController
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
}
