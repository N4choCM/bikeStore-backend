package com.withnacho.bikestore.demo.service;

import com.withnacho.bikestore.demo.entity.Product;
import com.withnacho.bikestore.demo.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> saveProduct(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> findProductById(Long id);
    public ResponseEntity<ProductResponseRest> findProductByName(String name);
    public ResponseEntity<ProductResponseRest> deleteProductById(Long id);
    public ResponseEntity<ProductResponseRest> findProducts();
    public ResponseEntity<ProductResponseRest> updateProduct(Product product, Long categoryId, Long id);

}
