package com.withnacho.bikestore.demo.response;

import java.util.List;

import com.withnacho.bikestore.demo.entity.Category;
import lombok.Data;

/**
 * Class containing a list of categories to be used by CategoryResponseRest.java to set the metadata of certain queries.
 * @see CategoryResponseRest
 */
@Data
public class CategoryResponse {

    private List<Category> categoryList;

}

