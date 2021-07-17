package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.dto.CategoryDto;

import java.util.List;

/**
 * Service interface for {@link Category}
 */
public interface CategoryService {

    List<CategoryDto> getAll();

}
