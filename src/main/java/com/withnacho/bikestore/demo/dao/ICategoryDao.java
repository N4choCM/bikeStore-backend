package com.withnacho.bikestore.demo.dao;

import com.withnacho.bikestore.demo.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDao extends CrudRepository<Category, Long>{

}
