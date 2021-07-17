package com.senla.courses.shops.model.dto;

import com.senla.courses.shops.model.Shop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of {@link Shop}
 */
@Getter
@Setter
public class ShopDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private String name;
    private String address;
}
