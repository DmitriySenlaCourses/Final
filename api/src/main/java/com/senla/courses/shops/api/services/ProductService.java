package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for {@link Product}
 */
public interface ProductService {
    ProductDto create(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);

    void delete(long id);

    List<ProductDto> findByCategoryAndName(String categoryName, String productName);

    Product findByName(String name);


}
