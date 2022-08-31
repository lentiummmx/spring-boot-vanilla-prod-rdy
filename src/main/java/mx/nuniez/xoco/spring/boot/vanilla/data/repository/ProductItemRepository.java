package mx.nuniez.xoco.spring.boot.vanilla.data.repository;

import mx.nuniez.xoco.spring.boot.vanilla.data.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
