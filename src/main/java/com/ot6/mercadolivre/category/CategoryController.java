package com.ot6.mercadolivre.category;

import com.ot6.mercadolivre.category.dtos.NewCategoryRequest;
import com.ot6.mercadolivre.category.dtos.NewCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<NewCategoryResponse> create(@RequestBody @Valid NewCategoryRequest categoryRequest) {
        Category category = categoryRequest.toEntity(categoryRepository);
        categoryRepository.save(category);
        return ResponseEntity.ok(category.toNewCategoryResponse());
    }
}
