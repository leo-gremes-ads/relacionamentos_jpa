package mesmo.eu.relacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.dto.IngredienteDto;
import mesmo.eu.relacionamento.entity.Ingrediente;
import mesmo.eu.relacionamento.mapper.IngredienteMapper;
import mesmo.eu.relacionamento.repository.IngredienteRepository;

@Service
public class IngredienteService
{
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private IngredienteMapper ingredienteMapper;
    
    @Transactional
    public IngredienteDto salvar(IngredienteDto dto)
    {
        Ingrediente ingrediente = ingredienteMapper.toEntity(dto);
        ingredienteRepository.save(ingrediente);
        return ingredienteMapper.toDto(ingrediente);
    }

    public List<IngredienteDto> listarTodos()
    {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        return ingredientes.stream().map(ing -> ingredienteMapper.toDto(ing)).toList();
    }

    public IngredienteDto listarUm(Long id)
    {
        Ingrediente ingrediente = ingredienteRepository.findById(id).orElse(null);
        return (ingrediente == null) ? null : ingredienteMapper.toDto(ingrediente);
    }

    @Transactional
    public void apagarPorId(Long id)
    {
        ingredienteRepository.deleteById(id);
    }

    @Transactional
    public IngredienteDto atualizar(Long id, IngredienteDto dto)
    {
        Ingrediente existente = ingredienteRepository.findById(id).orElse(null);
        if (existente == null)
            throw new EntityNotFoundException("Ingrediente com id " + id + " inexistente");
        Ingrediente novo = ingredienteMapper.toEntity(dto);
        novo.setId(id);
        novo = ingredienteRepository.save(novo);
        return ingredienteMapper.toDto(novo);        
    }

    @Transactional
    public IngredienteDto atualizarParcial(Long id, IngredienteDto dto)
    {
        Ingrediente existente = ingredienteRepository.findById(id).orElse(null);
        if (existente == null)
            throw new EntityNotFoundException("Ingrediente com id " + id + " inexistente");
        ingredienteMapper.updateFromDto(dto, existente);
        existente = ingredienteRepository.save(existente);
        return ingredienteMapper.toDto(existente);
    }
}