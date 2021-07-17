package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController for {@link Category}
 */
@RestController
@RequestMapping("/categories")
@Api(tags = {"Category controller"}, description = "Some operations on the category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Get all categories")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All categories successfully received")})
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> list = categoryService.getAll();

        return ResponseEntity.ok(list);
    }
}
