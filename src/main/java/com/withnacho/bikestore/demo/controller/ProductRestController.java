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
     * REST Request for saving a Product in the DB.
     * @param picture The picture of the Product to be saved.
     * @param name The name of the Product to be saved.
     * @param price The price of the Product to be saved.
     * @param quantity The amount of Products to be saved.
     * @param categoryID Foreign Key of the Category associated to the Product to be saved.
     * @return The Saved Product.
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
     * REST Request for getting a Product by its ID.
     * @param id The ID of the Product to be found.
     * @return The Product found.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    /**
     * REST Request for getting a Product by its name.
     * @param name The Name of the Product to be found.
     * @return The Product found.
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> findProductByName(@PathVariable String name){
        return productService.findProductByName(name);
    }

    /**
     * REST Request for deleting a Product by its ID.
     * @param id The ID of the Product to be deleted.
     * @return The Product deleted.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }

    /**
     * REST Request for getting all the Products in the DB.
     * @return All the Products in the DB.
     */
    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> findProducts(){
        return productService.findProducts();
    }

    /**
     * REST Request for updating a Product in the DB.
     * @param picture The picture of the Product to be updated.
     * @param name The name of the Product to be updated.
     * @param price The price of the Product to be updated.
     * @param quantity The amount of the Product to be updated.
     * @param categoryID Foreign key of the Category associated to the Product to be updated.
     * @param id The ID of the Product to be updated.
     * @return The Product updated.
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
     * REST Request for exporting an EXCEL file with all the Products.
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