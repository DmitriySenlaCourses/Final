package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.ShopDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for {@link Shop}
 */
@RestController
@RequestMapping("/shops")
@Api(tags = {"Shop controller. Some operations on the shop"})
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    @ApiOperation(value = "Get all shops")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All shops successfully received")})
    public ResponseEntity<List<ShopDto>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "id") String sortBy) {
        List<ShopDto> list = shopService.getAll(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @ApiOperation(value = "Create new shop")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Shop was created successfully"),
            @ApiResponse(code = 400, message = "Shop already exists")})
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto) {
        ShopDto returnedShopDto = shopService.create(shopDto);
        return ResponseEntity.ok(returnedShopDto);
    }
}
