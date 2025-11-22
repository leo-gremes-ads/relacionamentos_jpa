package mesmo.eu.relacionamento.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.ItemDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoSaida;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.ItemId;
import mesmo.eu.relacionamento.entity.Pedido;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.mapper.PedidoMapper;
import mesmo.eu.relacionamento.repository.PedidoRepository;
import mesmo.eu.relacionamento.repository.ProdutoRepository;

@Service
public class PedidoService
{
    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public PedidoDto salvar(PedidoDto dto)
    {
        Pedido pedido = new Pedido();
        pedido.setNumeroMesa(dto.numeroMesa());
        pedido.setData(LocalDate.now());
        pedido = pedidoRepository.save(pedido);
        List<Item> itens = pedidoMapper.toEntityList(dto.itens(), pedido, produtoRepository);
        pedido.setItens(itens);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    public List<PedidoSaida> procurarTodos()
    {
        List<Pedido> lista = pedidoRepository.findAll();
        Function<Pedido, PedidoSaida> mapper = p -> {
            Integer qtdItens = p.getItens().size();
            Double valorTotal = p.getItens().stream().map(it -> it.getQtd() * it.getProduto().getPreco()).reduce(Double::sum).orElse(0.0);
            PedidoSaida saida = new PedidoSaida(p.getId(), p.getData(), p.getNumeroMesa(), qtdItens, valorTotal);
            return saida;    
        };
        return lista.stream().map(mapper).toList();
    }   

    public PedidoDto procurarPorId(Long id)
    {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null)
            return null;
        return pedidoMapper.toDto(pedido);
    }

    @Transactional
    public void deletarPorId(Long id)
    {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public PedidoDto atualizarTotal(Long id, PedidoDto dto)
    {
        Pedido existente = pedidoRepository.findById(id).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Pedido inexistente");
        Pedido novo = pedidoMapper.toEntity(dto);
        novo.setData(dto.data());
        novo.setId(id);
        novo.setNumeroMesa(dto.numeroMesa());
        List<Item> itens = pedidoMapper.toEntityList(dto.itens(), novo, produtoRepository);
        novo.setItens(itens);
        novo = pedidoRepository.save(novo);
        return pedidoMapper.toDto(novo);
    }

    @Transactional
    public PedidoDto atualizarParcial(Long id, PedidoDto dto)
    {
        Pedido existente = pedidoRepository.findById(id).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Pedido inexistente");
        pedidoMapper.updateFromDto(dto, existente);
        if (dto.itens() != null) {
            List<Item> itens = pedidoMapper.toEntityList(dto.itens(), existente, produtoRepository);
            existente.getItens().clear();
            existente.getItens().addAll(itens);
        }
        existente = pedidoRepository.save(existente);
        return pedidoMapper.toDto(existente);
    }

    @Transactional
    public PedidoDto adicionarItem(Long id, ItemDto dto)
    {
        Pedido existente = pedidoRepository.findById(id).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Pedido inexistente");
        Produto produto = produtoRepository.findById(dto.produtoId()).orElse(null);
        if (produto == null) throw new EntityNotFoundException("Produto inexistente");
        int seq = existente.getItens().size() + 1;
        Item item = new Item();
        item.setPedido(existente);
        item.setProduto(produto);
        item.setQtd(dto.qtd());
        ItemId itemId = new ItemId(existente.getId(), seq);
        item.setId(itemId);
        existente.getItens().add(item);
        existente = pedidoRepository.save(existente);
        return pedidoMapper.toDto(existente);
    }

    @Transactional
    public PedidoDto removerItem(Long id, Integer seq)
    {
        Pedido existente = pedidoRepository.findById(id).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Pedido inexistente");
        Item item = existente.getItens().stream().filter(i -> i.getId().getSeq() == seq)
            .findAny().orElse(null);
        if (item == null) throw new EntityNotFoundException("Item não encontrado");
        existente.getItens().remove(item);
        List<Item> novaLista = new ArrayList<>();
        List<Item> listaOriginal = existente.getItens();
        int contador = 1;
        for (Item it : listaOriginal) {
            Item novoItem = new Item();
            ItemId itemId = new ItemId(existente.getId(), contador++);
            novoItem.setId(itemId);
            novoItem.setPedido(existente);
            novoItem.setProduto(it.getProduto());
            novoItem.setQtd(it.getQtd());
            novaLista.add(novoItem);
        }
        existente.getItens().clear();
        existente = pedidoRepository.save(existente);
        existente.getItens().addAll(novaLista);
        existente = pedidoRepository.save(existente);
        return pedidoMapper.toDto(existente);
    }

    @Transactional
    public PedidoDto substituirItem(Long id, Integer seq, ItemDto dto)
    {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido inexistente"));
        Item atual = pedido.getItens().stream()
            .filter(i -> i.getId().getSeq() == seq).findAny()
            .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
        Produto produto = produtoRepository.findById(dto.produtoId())
            .orElseThrow(() -> new EntityNotFoundException("Produto inexistente"));
        atual.setProduto(produto);
        atual.setQtd(dto.qtd());
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    @Transactional
    public PedidoDto atualizarItem(Long id, Integer seq, ItemDto dto)
    {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido inexistente"));
        Item atual = pedido.getItens().stream()
            .filter(i -> i.getId().getSeq() == seq).findAny()
            .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
        pedidoMapper.updateFromDto(dto, atual, produtoRepository);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);       
    }
}