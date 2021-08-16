package com.ot6.mercadolivre.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuestionRepository extends CrudRepository<ProductQuestion, Long> {
}
