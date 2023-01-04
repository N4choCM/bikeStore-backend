package com.withnacho.bikestore.demo.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that instantiates a ProductResponse and inherits from ResponseRest to set the metadata of certain queries.
 * @see ProductResponse
 * @see ResponseRest
 */
@Getter
@Setter
public class ProductResponseRest extends ResponseRest{

    private ProductResponse productResponse = new ProductResponse();

}
