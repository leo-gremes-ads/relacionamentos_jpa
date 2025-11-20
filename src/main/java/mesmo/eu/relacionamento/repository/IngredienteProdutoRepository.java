package mesmo.eu.relacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.entity.IngredienteProduto;
import mesmo.eu.relacionamento.entity.IngredienteProdutoId;

@Repository
@Transactional
public interface IngredienteProdutoRepository extends JpaRepository<IngredienteProduto, IngredienteProdutoId>
{
    
}