package mesmo.eu.relacionamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.IngredienteProdutoDto;
import mesmo.eu.relacionamento.dto.ProdutoDto;
import mesmo.eu.relacionamento.entity.Ingrediente;
import mesmo.eu.relacionamento.entity.IngredienteProduto;
import mesmo.eu.relacionamento.entity.IngredienteProdutoId;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.mapper.ProdutoMapper;
import mesmo.eu.relacionamento.repository.IngredienteRepository;
import mesmo.eu.relacionamento.repository.ProdutoRepository;

@Service
public class ProdutoService
{
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Transactional
    public ProdutoDto salvar(ProdutoDto dto)
    {
        Produto produto = produtoMapper.toEntity(dto);
        produto = produtoRepository.save(produto);
        if (dto.ingredientes() != null && dto.ingredientes().size() > 0) {
            List<IngredienteProduto> ingredientes = dto.ingredientes().stream()
                .map(d -> {
                    IngredienteProduto ingredienteProduto = produtoMapper.toEntity(d);
                    Ingrediente ingrediente = ingredienteRepository
                        .findById(d.ingredienteId()).orElse(null);
                    ingredienteProduto.setIngrediente(ingrediente);
                    return ingredienteProduto;
                })
                .collect(Collectors.toList());
            for (IngredienteProduto ing : ingredientes ) {
                IngredienteProdutoId id = new IngredienteProdutoId(
                    produto.getId(), ing.getIngrediente().getId()
                );
                ing.setId(id);
                ing.setProduto(produto);
            }
            produto.setIngredientes(ingredientes);
        }
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }

    @Transactional
    public ProdutoDto atualizarTotal(Long id, ProdutoDto dto)
    {
        Produto existente = produtoRepository.findById(id).orElse(null);
        if (existente == null)
            throw new EntityNotFoundException("Produto com id " +
                id + " inexistente");
        Produto novo = produtoMapper.toEntity(dto);
        novo.setId(id);
        if (dto.ingredientes() != null && dto.ingredientes().size() > 0) {
            List<IngredienteProduto> ingredientes = dto.ingredientes().stream()
                .map(d -> {
                    IngredienteProduto ip = produtoMapper.toEntity(d);
                    Ingrediente ingrediente = ingredienteRepository
                        .findById(d.ingredienteId()).orElse(null);
                    ip.setIngrediente(ingrediente);
                    return ip;
                })
                .collect(Collectors.toList());
            for (IngredienteProduto ing : ingredientes) {
                IngredienteProdutoId ipId = new IngredienteProdutoId(
                    novo.getId(), ing.getIngrediente().getId()
                );
                ing.setId(ipId);
                ing.setProduto(novo);
            }
            novo.setIngredientes(ingredientes);
        }
        novo = produtoRepository.save(novo);
        return produtoMapper.toDto(novo);
    }

    public ProdutoDto atualizarParcial(Long id, ProdutoDto dto)
    {
        Produto existente = produtoRepository.findById(id).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Produto inexistente");
        produtoMapper.updateFromDto(dto, existente);
        existente = produtoRepository.save(existente);
        return produtoMapper.toDto(existente);
    }

    public List<ProdutoDto> procurarTodos()
    {
        return produtoRepository.findAll().stream()
            .map(p -> produtoMapper.toDto(p))
            .toList();
    }

    public ProdutoDto procurarPorId(Long id)
    {
        Produto produto = produtoRepository.findById(id).orElse(null);
        return (produto == null) ? null : produtoMapper.toDto(produto);
    }

    @Transactional
    public void deletarPorId(Long id)
    {
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoDto adicionarIngrediente(Long id, IngredienteProdutoDto dto)
    {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) throw new EntityNotFoundException("Produto inexistente");
        IngredienteProduto ip = produtoMapper.toEntity(dto);
        Ingrediente ing = ingredienteRepository.findById(dto.ingredienteId()).orElse(null);
        if (ing == null) throw new EntityNotFoundException("Ingrediente inexistente");
        IngredienteProdutoId ipId = new IngredienteProdutoId(produto.getId(), ing.getId());
        ip.setProduto(produto);
        ip.setIngrediente(ing);
        ip.setId(ipId);
        produto.getIngredientes().add(ip);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }

    @Transactional
    public ProdutoDto removerIngrediente(Long produtoId, Long ingredienteId)
    {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) throw new EntityNotFoundException("Produto inexistente");
        IngredienteProduto ip = produto.getIngredientes().stream()
            .filter(i -> i.getId().getIngredienteId() == ingredienteId)
            .findAny().orElse(null);
        if (ip == null) throw new EntityNotFoundException("Ingrediente inexistente na receita");
        produto.getIngredientes().remove(ip);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }

    @Transactional
    public ProdutoDto substituirIngrediente(Long produtoId, Long ingredienteId,
        IngredienteProdutoDto dto)
    {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) throw new EntityNotFoundException("Produto inexistente");
        removerIngrediente(produto.getId(), ingredienteId);
        adicionarIngrediente(produto.getId(), dto);
        return produtoMapper.toDto(produto);
    }

    @Transactional
    public ProdutoDto atualizarIngrediente(Long produtoId, Long ingredienteId,
        IngredienteProdutoDto dto)
    {
        Produto existente = produtoRepository.findById(produtoId).orElse(null);
        if (existente == null) throw new EntityNotFoundException("Produto inexistente");
        IngredienteProduto ip = existente.getIngredientes().stream()
            .filter(i -> i.getId().getIngredienteId() == ingredienteId)
            .findAny().orElse(null);
        produtoMapper.updateFromDto(dto, ip);
        existente = produtoRepository.save(existente);
        return produtoMapper.toDto(existente);
    }
}