package mx.nuniez.xoco.spring.boot.vanilla.data.repository;

import mx.nuniez.xoco.spring.boot.vanilla.data.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    @Query(name = "qwerty", value = "from ProductItem where id = ?1")
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.comment", value = "NoNestLoopQueryHintHandler"),
            @QueryHint(name = "QWERTY_ASDFGH", value = "/*+NoNestLoop(product_item)")
    })
    ProductItem customSelectById(Long id);

}
