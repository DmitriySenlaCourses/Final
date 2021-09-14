package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.ProductService;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * RestController for {@link Product}
 */
@RestController
@RequestMapping("/products")
@Api(tags = {"Product controller"}, description = "Some operations on the product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    @ApiOperation(value = "Find product by category and name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of product was received successfully"),
            @ApiResponse(code = 400, message = "Category not found")})
    public ResponseEntity<List<ProductDto>> findByCategory(@RequestParam(name = "category") String categoryName,
                                                           @RequestParam(name = "name", required = false) String productName) {
        List<ProductDto> list = productService.findByCategoryAndName(categoryName, productName);

        return ResponseEntity.ok(list);
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product was created successfully"),
            @ApiResponse(code = 400, message = "Product already exists")})
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto returnedProductDto = productService.create(productDto);
        return ResponseEntity.ok(returnedProductDto);
    }

    @PutMapping
    @ApiOperation(value = "Update product")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product was updated successfully"),
            @ApiResponse(code = 400, message = "Product already exists")})
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto returnedProductDto = productService.update(productDto);
        return ResponseEntity.ok(returnedProductDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Product was deleted successfully"),
    @ApiResponse(code = 400, message = "Product id not found")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
