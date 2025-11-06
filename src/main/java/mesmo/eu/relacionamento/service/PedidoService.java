package mesmo.eu.relacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.PedidoDto;
import mesmo.eu.relacionamento.entity.Pedido;
import mesmo.eu.relacionamento.mapper.PedidoMapper;
import mesmo.eu.relacionamento.repository.PedidoRepository;

@Service
public class PedidoService
{
    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido salvar(PedidoDto dto)
    {
        Pedido p = pedidoMapper.toEntity(dto);
        return pedidoRepository.save(p);
    }

    public List<Pedido> procurarTodos()
    {
        return pedidoRepository.findAll();
    }   
}