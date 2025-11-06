package mesmo.eu.relacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.ItemId;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, ItemId>
{
    
}
