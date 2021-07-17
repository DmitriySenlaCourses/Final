package com.senla.courses.shops.model.dto;

import com.senla.courses.shops.model.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of {@link Product}
 */
@Getter
@Setter
public class ProductDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private String name;
    private CategoryDto category;
    private String unit;
}
