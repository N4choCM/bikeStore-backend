package com.withnacho.bikestore.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.withnacho.bikestore.demo.dao.ICategoryDao;
import com.withnacho.bikestore.demo.entity.Category;
import com.withnacho.bikestore.demo.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategoryDao categoryDao;

    /**
     * REST Request for getting all the Categories in the DB.
     * @return OK if all the Categories were retrieved or ERROR if something unexpected happened.
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> findCategories() {

        CategoryResponseRest categoriesResponse = new CategoryResponseRest();

        try {
            List<Category> categoriesList = (List<Category>) categoryDao.findAll();
            categoriesResponse.getCategoryResponse().setCategoryList(categoriesList);
            categoriesResponse.setMetadata("OK", "00", "Categories found");

        } catch (Exception e) {

            categoriesResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.OK);
    }

    /**
     * REST Request for getting a Category by its ID.
     * @param id The ID of the Category to be found.
     * @return OK if the Category was found or ERROR if it was not or something wrong happened.
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> findCategoryById(Long id) {

        CategoryResponseRest categoriesResponse = new CategoryResponseRest();
        List<Category> categoriesList = new ArrayList<>();

        try {

            Optional<Category> category = categoryDao.findById(id);

            if (category.isPresent()) {
                categoriesList.add(category.get());
                categoriesResponse.getCategoryResponse().setCategoryList(categoriesList);
                categoriesResponse.setMetadata("OK", "00", "Category found");
            } else {
                categoriesResponse.setMetadata("ERROR", "-1", "Category not found");
                return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {

            categoriesResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        }

        return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.OK);
    }

    /**
     * REST Request for saving a Category in the DB.
     * @param category The data of the Category to be saved.
     * @return OK if the Category was successfully saved or ERROR if it was not or something wrong happened.
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> saveCategory(Category category) {

        CategoryResponseRest categoriesResponse = new CategoryResponseRest();
        List<Category> categoriesList = new ArrayList<>();

        try {

            Category categorySaved = categoryDao.save(category);

            if (categorySaved != null) {
                categoriesList.add(categorySaved);
                categoriesResponse.getCategoryResponse().setCategoryList(categoriesList);
                categoriesResponse.setMetadata("OK", "00", "Category saved");
            } else {
                categoriesResponse.setMetadata("ERROR", "-1", "Category not saved");
                return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.BAD_REQUEST);
            }


        } catch (Exception e) {

            categoriesResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        }

        return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.OK);
    }

    /**
     * REST Request for updating a Category in the DB.
     * @param category The data of the Category to be updated.
     * @param id The ID of the Category to be updated.
     * @return OK if the Category was successfully updated or ERROR if it was not or something wrong happened.
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> updateCategory(Category category, Long id) {
        CategoryResponseRest categoriesResponse = new CategoryResponseRest();
        List<Category> categoriesList = new ArrayList<>();

        try {

            Optional<Category> categorySearch = categoryDao.findById(id);

            if (categorySearch.isPresent()) {
                // se procederá a actualizar el registro
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());

                Category categoryToUpdate = categoryDao.save(categorySearch.get());

                if (categoryToUpdate != null) {
                    categoriesList.add(categoryToUpdate);
                    categoriesResponse.getCategoryResponse().setCategoryList(categoriesList);
                    categoriesResponse.setMetadata("OK", "00", "Category updated");
                } else {
                    categoriesResponse.setMetadata("ERROR", "-1", "Category not updated");
                    return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.BAD_REQUEST);
                }


            } else {
                categoriesResponse.setMetadata("ERROR", "-1", "Category not found");
                return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {

            categoriesResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        }

        return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.OK);
    }

    /**
     * REST Request for deleting a Category from the DB.
     * @param id The ID of the Category to be deleted.
     * @return OK if the Category was successfully deleted or ERROR if it was not.
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteCategoryById(Long id) {

        CategoryResponseRest categoriesResponse = new CategoryResponseRest();

        try {

            categoryDao.deleteById(id);
            categoriesResponse.setMetadata("OK", "00", "Category deleted");


        } catch (Exception e) {

            categoriesResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        }

        return new ResponseEntity<CategoryResponseRest>(categoriesResponse, HttpStatus.OK);

    }

}

