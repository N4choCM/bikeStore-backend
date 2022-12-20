package com.withnacho.bikestore.demo.response;

import java.util.List;

import com.withnacho.bikestore.demo.entity.Category;
import lombok.Data;

@Data
public class CategoryResponse {

    private List<Category> categoryList;

}

