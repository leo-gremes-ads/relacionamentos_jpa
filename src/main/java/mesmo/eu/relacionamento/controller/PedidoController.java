package mesmo.eu.relacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mesmo.eu.relacionamento.dto.PedidoDto;
import mesmo.eu.relacionamento.entity.Pedido;
import mesmo.eu.relacionamento.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController
{
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> novoPedido(@RequestBody PedidoDto dto)
    {
        Pedido p = pedidoService.salvar(dto);
        return ResponseEntity.ok(p);        
    }

    @GetMapping
    public List<Pedido> listarTodos()
    {
        return pedidoService.procurarTodos();
    }
}
