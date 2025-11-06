package mesmo.eu.relacionamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.PedidoDto;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.ItemId;
import mesmo.eu.relacionamento.entity.Pedido;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.mapper.PedidoMapper;
import mesmo.eu.relacionamento.repository.PedidoRepository;

@Service
public class PedidoService
{
    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public PedidoDto salvar(PedidoDto dto)
    {
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.cliente());
        pedido = pedidoRepository.save(pedido);
        List<Item> itens = dto.itens().stream()
            .map(d -> {
                Item item = pedidoMapper.toEntity(d);
                pedidoMapper.toEntity(d);
                Produto produto = produtoService.procurarPorId(d.produtoId());
                item.setProduto(produto);
                return item;
            })
            .collect(Collectors.toList());
        int contador = 1;
        for (Item item : itens) {
            ItemId id = new ItemId(pedido.getId(), contador++);
            item.setId(id);
            item.setPedido(pedido);
        }
        pedido.setItens(itens);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    public List<PedidoDto> procurarTodos()
    {
        return pedidoRepository.findAll()
            .stream().map(pedido -> pedidoMapper.toDto(pedido))
            .toList();
    }   
}