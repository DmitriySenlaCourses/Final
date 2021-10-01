package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.model.Price;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * RestController for {@link Price}
 */
@RestController
@RequestMapping("/prices")
@Api(tags = {"Price controller"}, description = "Some operations on the price")
public class PriceController {
    private PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/dynamics")
    @ApiOperation(value = "Get price dynamics for a shop product")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Dynamics was received successfully"),
            @ApiResponse(code = 400, message = "Product or shop not found")})
    public ResponseEntity<Map<LocalDate, BigDecimal>> getPriceDynamics(@RequestParam(name = "product") String productName,
                                                                       @RequestParam(name = "shop") String shopName,
                                                                       @RequestParam(name = "address") String shopAddress,
                                                                       @RequestParam(name = "start", required = false) @ApiParam(example = "2021-10-25") String startDate,
                                                                       @RequestParam(name = "end", required = false) @ApiParam(example = "2021-10-25") String endDate,
                                                                       @RequestParam(defaultValue = "0") Integer pageNo,
                                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(defaultValue = "date") String sortBy) {
        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, startDate, endDate, pageNo, pageSize, sortBy);
        return ResponseEntity.ok(dynamics);
    }

    @GetMapping("/comparing")
    @ApiOperation(value = "Comparing of product prices for all shops")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Comparing was received successfully"),
            @ApiResponse(code = 400, message = "Product not found")})
    public ResponseEntity<List<PriceShop>> getLastPrices(@RequestParam(name = "product") String productName,
                                                         @RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "date") String sortBy) {
        List<PriceShop> lastPrices = priceService.getLastPrices(productName, pageNo, pageSize, sortBy);
        return ResponseEntity.ok(lastPrices);
    }
}
