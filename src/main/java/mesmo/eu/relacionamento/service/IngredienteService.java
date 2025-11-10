package mesmo.eu.relacionamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
