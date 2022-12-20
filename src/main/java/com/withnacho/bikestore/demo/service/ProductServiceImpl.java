package com.withnacho.bikestore.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.withnacho.bikestore.demo.dao.ICategoryDao;
import com.withnacho.bikestore.demo.dao.IProductDao;
import com.withnacho.bikestore.demo.entity.Category;
import com.withnacho.bikestore.demo.entity.Product;
import com.withnacho.bikestore.demo.response.ProductResponseRest;
import com.withnacho.bikestore.demo.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements IProductService {

    private ICategoryDao categoryDao;
    private IProductDao productDao;

    public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
        super();
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> saveProduct(Product product, Long categoryId) {

        ProductResponseRest productsResponse = new ProductResponseRest();
        List<Product> productsList = new ArrayList<>();

        try {

            //search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);

            if( category.isPresent()) {
                product.setCategory(category.get());
            } else {
                productsResponse.setMetadata("ERROR", "-1", "Product Category not found");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.NOT_FOUND);
            }


            //save the product
            Product productSaved = productDao.save(product);

            if (productSaved != null) {
                productsList.add(productSaved);
                productsResponse.getProductResponse().setProductsList(productsList);
                productsResponse.setMetadata("OK", "00", "Product saved");

            } else {
                productsResponse.setMetadata("ERROR", "-1", "Product not saved ");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.BAD_REQUEST);

            }


        } catch (Exception e) {
            e.getStackTrace();
            productsResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.OK);


    }

    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<ProductResponseRest> findProductById(Long id) {

        ProductResponseRest productsResponse = new ProductResponseRest();
        List<Product> productsList = new ArrayList<>();

        try {

            //search producto by id
            Optional<Product> product = productDao.findById(id);

            if( product.isPresent()) {

                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                productsList.add(product.get());
                productsResponse.getProductResponse().setProductsList(productsList);
                productsResponse.setMetadata("OK", "00", "Product found");

            } else {
                productsResponse.setMetadata("ERROR", "-1", "Product not found");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            e.getStackTrace();
            productsResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.OK);

    }

    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<ProductResponseRest> findProductByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> productsList = new ArrayList<>();
        List<Product> auxiliaryProductsList = new ArrayList<>();

        try {

            //search producto by name
            auxiliaryProductsList = productDao.findByNameContainingIgnoreCase(name);


            if( auxiliaryProductsList.size() > 0) {

                auxiliaryProductsList.stream().forEach( (element) -> {
                    byte[] imageDescompressed = Util.decompressZLib(element.getPicture());
                    element.setPicture(imageDescompressed);
                    productsList.add(element);
                });


                response.getProductResponse().setProductsList(productsList);
                response.setMetadata("OK", "00", "Products found");

            } else {
                response.setMetadata("ERROR", "-1", "Products not found ");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteProductById(Long id) {
        ProductResponseRest productsResponse = new ProductResponseRest();

        try {

            //delete producto by id
            productDao.deleteById(id);
            productsResponse.setMetadata("OK", "00", "Product deleted");


        } catch (Exception e) {
            e.getStackTrace();
            productsResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.OK);
    }

    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<ProductResponseRest> findProducts() {
        ProductResponseRest productsResponse = new ProductResponseRest();
        List<Product> productsList = new ArrayList<>();
        List<Product> auxiliaryProductsList = new ArrayList<>();

        try {

            //search producto
            auxiliaryProductsList = (List<Product>) productDao.findAll();


            if( auxiliaryProductsList.size() > 0) {

                auxiliaryProductsList.stream().forEach( (p) -> {
                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    productsList.add(p);
                });


                productsResponse.getProductResponse().setProductsList(productsList);
                productsResponse.setMetadata("OK", "00", "Products found");

            } else {
                productsResponse.setMetadata("ERROR", "-1", "Products not found ");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            e.getStackTrace();
            productsResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> updateProduct(Product product, Long categoryId, Long id) {
        ProductResponseRest productsResponse = new ProductResponseRest();
        List<Product> productsList = new ArrayList<>();

        try {

            //search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);

            if( category.isPresent()) {
                product.setCategory(category.get());
            } else {
                productsResponse.setMetadata("ERROR", "-1", "Product Ctegory not found");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.NOT_FOUND);
            }


            //search Product to update
            Optional<Product> productSearch = productDao.findById(id);

            if (productSearch.isPresent()) {

                //se actualizará el producto
                productSearch.get().setQuantity(product.getQuantity());
                productSearch.get().setCategory(product.getCategory());
                productSearch.get().setName(product.getName());
                productSearch.get().setPicture(product.getPicture());
                productSearch.get().setPrice(product.getPrice());

                //save the product in DB
                Product productToUpdate = productDao.save(productSearch.get());

                if (productToUpdate != null) {
                    productsList.add(productToUpdate);
                    productsResponse.getProductResponse().setProductsList(productsList);
                    productsResponse.setMetadata("OK", "00", "Product updated");
                } else {
                    productsResponse.setMetadata("ERROR", "-1", "Product not updated");
                    return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.BAD_REQUEST);

                }

            } else {
                productsResponse.setMetadata("ERROR", "-1", "Product not updated");
                return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.NOT_FOUND);

            }


        } catch (Exception e) {
            e.getStackTrace();
            productsResponse.setMetadata("ERROR", "-1", "INTERNAL SERVER ERROR");
            return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<ProductResponseRest>(productsResponse, HttpStatus.OK);
    }



}
