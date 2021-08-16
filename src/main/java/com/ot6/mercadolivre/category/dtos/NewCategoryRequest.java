package com.ot6.mercadolivre.category.dtos;

import com.ot6.mercadolivre.category.Category;
import com.ot6.mercadolivre.category.CategoryRepository;
import com.ot6.mercadolivre.shared.validation.ExistingId;
import com.ot6.mercadolivre.shared.validation.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class NewCategoryRequest {

    @NotBlank
    @Unique(field = "name", theClass = Category.class, message = "Nome da categoria já em uso")
    private String name;

    @Positive
    @ExistingId(field = "id", theClass = Category.class, message = "A categoria informada deve estar registrada")
    private Long motherCategoryId;

    public NewCategoryRequest(String name, Long motherCategoryId) {
        this.name = name;
        this.motherCategoryId = motherCategoryId;
    }

    public Category toEntity(CategoryRepository categoryRepository) {
        Category category = new Category(this.name);

        if (motherCategoryId != null) {
            Optional<Category> motherCategory = categoryRepository.findById(motherCategoryId);
            category.setMother(motherCategory.get());
        }

        return category;
    }
}
