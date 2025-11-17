package mesmo.eu.relacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.mapper.ProdutoMapper;
import mesmo.eu.relacionamento.repository.ProdutoRepository;

@Service
public class ProdutoService
{
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ProdutoMapper produtoMapper;

    @Transactional
    public Produto salvar(ProdutoDto dto)
    {
        Produto produto = produtoMapper.toEntity(dto);
        return produtoRepository.save(produto);
    }

    public List<Produto> procurarTodos()
    {
        return produtoRepository.findAll();
    }

    public Produto procurarPorId(Long id)
    {
        return produtoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deletarPorId(Long id)
    {
        produtoRepository.deleteById(id);
    }
}
