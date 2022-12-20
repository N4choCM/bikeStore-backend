package com.withnacho.bikestore.demo.service;

import com.withnacho.bikestore.demo.entity.Category;
import com.withnacho.bikestore.demo.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    public ResponseEntity<CategoryResponseRest> findCategories();
    public ResponseEntity<CategoryResponseRest> findCategoryById(Long id);
    public ResponseEntity<CategoryResponseRest> saveCategory(Category category);
    public ResponseEntity<CategoryResponseRest> updateCategory(Category category, Long id);
    public ResponseEntity<CategoryResponseRest> deleteCategoryById(Long id);

}
