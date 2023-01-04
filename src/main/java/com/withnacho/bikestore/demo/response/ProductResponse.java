package com.withnacho.bikestore.demo.response;

import java.util.List;

import com.withnacho.bikestore.demo.entity.Product;
import lombok.Data;

/**
 * Class containing a list of Products to be used by ProductResponseRest.java to set the metadata of certain queries.
 * @see ProductResponseRest
 */
@Data
public class ProductResponse {

    List<Product> productsList;

}
