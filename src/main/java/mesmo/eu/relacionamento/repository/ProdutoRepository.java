package mesmo.eu.relacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.entity.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long>
{
    
}
