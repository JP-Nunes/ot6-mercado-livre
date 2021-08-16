package com.ot6.mercadolivre.product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOpinionRepository extends CrudRepository<ProductOpinion, Long> {

    List<ProductOpinion> findAllByProductId(Long id);

    @Query(
        value = "select avg(o.grade) from product_opinions o where o.product_id = :productId",
        nativeQuery = true
    )
    Optional<Long> getProductAverageEvaluation(Long productId);
}
