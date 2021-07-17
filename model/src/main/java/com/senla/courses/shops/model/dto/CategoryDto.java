package com.senla.courses.shops.model.dto;

import com.senla.courses.shops.model.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of {@link Category}
 */
@Getter
@Setter
public class CategoryDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private String name;
}
