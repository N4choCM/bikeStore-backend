package com.withnacho.bikestore.demo.controller;

import java.io.IOException;

import com.withnacho.bikestore.demo.entity.Product;
import com.withnacho.bikestore.demo.response.ProductResponseRest;
import com.withnacho.bikestore.demo.service.IProductService;
import com.withnacho.bikestore.demo.util.ProductExcelExporter;
import com.withnacho.bikestore.demo.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    private IProductService productService;

    public ProductRestController(IProductService productService) {
        super();
        this.productService = productService;
    }

    /**
     *
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryID
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> saveProduct(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categoryId") Long categoryID) throws IOException
    {

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        return productService.saveProduct(product, categoryID);


    }

    /**
     * search by id
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }


    /**
     * search by name
     * @param name
     * @return
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> findProductByName(@PathVariable String name){
        return productService.findProductByName(name);
    }

    /**
     * delete by id
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }

    /**
     * get products
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> findProducts(){
        return productService.findProducts();
    }


    /**
     * update product
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryID
     * @param id
     * @return
     * @throws IOException
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> updateProduct(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categoryId") Long categoryID,
            @PathVariable Long id) throws IOException
    {

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        return productService.updateProduct(product, categoryID, id);


    }

    /**
     * export product in excel file
     * @param response
     * @throws IOException
     */
    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_product";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ProductResponseRest> productsResponse = productService.findProducts();

        ProductExcelExporter excelExporter = new ProductExcelExporter(
                productsResponse.getBody().getProductResponse().getProductsList());

        excelExporter.export(response);


    }



}
