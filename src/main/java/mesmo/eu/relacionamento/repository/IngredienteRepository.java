package mesmo.eu.relacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.entity.Ingrediente;

@Repository
@Transactional
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>
{
    
}
