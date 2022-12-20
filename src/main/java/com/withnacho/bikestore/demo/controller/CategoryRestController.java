package com.withnacho.bikestore.demo.controller;

import java.io.IOException;

import com.withnacho.bikestore.demo.entity.Category;
import com.withnacho.bikestore.demo.response.CategoryResponseRest;
import com.withnacho.bikestore.demo.service.ICategoryService;
import com.withnacho.bikestore.demo.util.CategoryExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

    @Autowired
    private ICategoryService service;


    /**
     * get all the categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> findCategories() {

        return service.findCategories();
    }

    /**
     * get categories by id
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> findCategoryById(@PathVariable Long id) {

        return service.findCategoryById(id);
    }

    /**
     * save categories
     * @param category
     * @return
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category) {

        return service.saveCategory(category);
    }

    /**
     * update categories
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> updateCategory(@RequestBody Category category, @PathVariable Long id) {

        return service.updateCategory(category, id);
    }

    /**
     * delete categorie
     * @param id
     * @return
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> deleteCategory(@PathVariable Long id) {

        return service.deleteCategoryById(id);
    }

    /**
     * export to excel file
     * @param response
     * @throws IOException
     */
    @GetMapping("/categories/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_category";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<CategoryResponseRest> categoriesResponse = service.findCategories();

        CategoryExcelExporter excelExporter = new CategoryExcelExporter(
                categoriesResponse.getBody().getCategoryResponse().getCategoryList());

        excelExporter.export(response);


    }

}
