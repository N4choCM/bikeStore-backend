package com.withnacho.bikestore.demo.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that instantiates a CategoryResponse and inherits from ResponseRest to set the metadata of certain queries.
 * @see CategoryResponse
 * @see ResponseRest
 */
@Getter
@Setter
public class CategoryResponseRest extends ResponseRest{

    private CategoryResponse categoryResponse = new CategoryResponse();

}
