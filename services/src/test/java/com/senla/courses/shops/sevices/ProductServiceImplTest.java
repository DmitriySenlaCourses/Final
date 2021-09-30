package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.ProductService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.CategoryRepository;
import com.senla.courses.shops.dao.PriceRepository;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.dao.ShopRepository;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.CategoryDto;
import com.senla.courses.shops.model.dto.ProductDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ModelMapper modelMapper;

    public ProductServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        Category category = new Category();
        category.setName("Fish");
        product.setCategory(category);
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(null);
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(categoryService.findByName(Mockito.anyString())).thenReturn(category);
        Mockito.when(categoryService.save(Mockito.any(Category.class))).thenReturn(category);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(productDto);

        productService.create(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(ProductDto.class), Mockito.eq(Product.class));
        Mockito.verify(categoryService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(categoryService, Mockito.never()).save(Mockito.any(Category.class));
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));

    }

    @Test(expected = EntityExistsException.class)
    public void createException() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        Category category = new Category();
        category.setName("Fish");
        product.setCategory(category);
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(product);
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(categoryService.findByName(Mockito.anyString())).thenReturn(category);
        Mockito.when(categoryService.save(Mockito.any(Category.class))).thenReturn(category);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(productDto);

        productService.create(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(ProductDto.class), Mockito.eq(Product.class));
        Mockito.verify(categoryService, Mockito.never()).findByName(Mockito.anyString());
        Mockito.verify(categoryService, Mockito.never()).save(Mockito.any(Category.class));
        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));

    }

    @Test
    public void update() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        Long id = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(null);
        Mockito.when(productRepository.getOne(1L)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        productService.update(1L, productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.times(1)).getOne(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
    }

    @Test(expected = EntityExistsException.class)
    public void updateException() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        Long id = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(product);
        Mockito.when(productRepository.getOne(1L)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        productService.update(id, productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.never()).getOne(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
    }

    @Test
    public void delete() {
        Long id = 1L;

        Mockito.when(productRepository.findById(1L)).thenReturn(new Product());
        Mockito.doNothing().when(productRepository).deleteById(1L);

        productService.delete(id);

        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void findByCategoryAndName() {
        String categoryName = "Fish";
        String productName = "Milk";
        Category category = new Category();
        List<Product> list = new ArrayList<>();
        list.add(new Product());
        list.add(new Product());

        Mockito.when(categoryService.findByName(categoryName)).thenReturn(category);
        Mockito.when(productRepository.findByCategory(category)).thenReturn(list);
        Mockito.when(productRepository.findByCategoryAndNameContaining(category, productName)).thenReturn(list);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(new ProductDto());

        List<ProductDto> returnedList = productService.findByCategoryAndName(categoryName, productName);

        Mockito.verify(categoryService, Mockito.times(1)).findByName(categoryName);
        Mockito.verify(productRepository, Mockito.never()).findByCategory(category);
        Mockito.verify(productRepository, Mockito.times(1)).findByCategoryAndNameContaining(category, productName);
        Mockito.verify(modelMapper, Mockito.times(2)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
        Assert.assertEquals(list.size(), returnedList.size());

    }

    @Test(expected = EntityNotFoundException.class)
    public void findByCategoryAndNameException() {
        String categoryName = "Fish";
        String productName = "Milk";
        Category category = new Category();
        List<Product> list = new ArrayList<>();
        list.add(new Product());
        list.add(new Product());

        Mockito.when(categoryService.findByName(categoryName)).thenReturn(null);
        Mockito.when(productRepository.findByCategory(category)).thenReturn(list);
        Mockito.when(productRepository.findByCategoryAndNameContaining(category, productName)).thenReturn(list);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(new ProductDto());

        List<ProductDto> returnedList = productService.findByCategoryAndName(categoryName, productName);

        Mockito.verify(categoryService, Mockito.times(1)).findByName(categoryName);
        Mockito.verify(productRepository, Mockito.never()).findByCategory(category);
        Mockito.verify(productRepository, Mockito.times(1)).findByCategoryAndNameContaining(category, productName);
        Mockito.verify(modelMapper, Mockito.times(2)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
        Assert.assertEquals(list.size(), returnedList.size());
    }
}