package com.withnacho.bikestore.demo.response;

import java.util.List;

import com.withnacho.bikestore.demo.entity.Product;
import lombok.Data;

@Data
public class ProductResponse {

    List<Product> productsList;

}
