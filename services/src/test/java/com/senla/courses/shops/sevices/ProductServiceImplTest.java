package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.ProductService;
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
    private CategoryRepository categoryRepository;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private PriceRepository priceRepository;
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
        Mockito.when(categoryRepository.findByNameEquals(Mockito.anyString())).thenReturn(category);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(productDto);

        productService.create(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(ProductDto.class), Mockito.eq(Product.class));
        Mockito.verify(categoryRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
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
        Mockito.when(categoryRepository.findByNameEquals(Mockito.anyString())).thenReturn(category);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(productDto);

        productService.create(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(ProductDto.class), Mockito.eq(Product.class));
        Mockito.verify(categoryRepository, Mockito.never()).findByNameEquals(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));

    }

    @Test
    public void update() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        productDto.setId(1L);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(null);
        Mockito.when(productRepository.getOne(1L)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        productService.update(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.times(1)).getOne(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
    }

    @Test(expected = EntityExistsException.class)
    public void updateException() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Milk");
        productDto.setId(1L);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Fish");
        productDto.setCategory(categoryDto);
        Product product = new Product();
        product.setName("Milk");

        Mockito.when(productRepository.findByNameEquals(productDto.getName())).thenReturn(product);
        Mockito.when(productRepository.getOne(1L)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        productService.update(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.never()).getOne(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
    }

    @Test
    public void delete() {
        Long id = 1L;

        Mockito.when(productRepository.getOne(1L)).thenReturn(new Product());
        Mockito.doNothing().when(productRepository).deleteById(1L);

        productService.delete(id);

        Mockito.verify(productRepository, Mockito.times(1)).getOne(1L);
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

        Mockito.when(categoryRepository.findByNameEquals(categoryName)).thenReturn(category);
        Mockito.when(productRepository.findByCategory(category)).thenReturn(list);
        Mockito.when(productRepository.findByCategoryAndNameContaining(category, productName)).thenReturn(list);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(new ProductDto());

        List<ProductDto> returnedList = productService.findByCategoryAndName(categoryName, productName);

        Mockito.verify(categoryRepository, Mockito.times(1)).findByNameEquals(categoryName);
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

        Mockito.when(categoryRepository.findByNameEquals(categoryName)).thenReturn(null);
        Mockito.when(productRepository.findByCategory(category)).thenReturn(list);
        Mockito.when(productRepository.findByCategoryAndNameContaining(category, productName)).thenReturn(list);
        Mockito.when(modelMapper.map(Mockito.any(Product.class), Mockito.eq(ProductDto.class))).thenReturn(new ProductDto());

        List<ProductDto> returnedList = productService.findByCategoryAndName(categoryName, productName);

        Mockito.verify(categoryRepository, Mockito.times(1)).findByNameEquals(categoryName);
        Mockito.verify(productRepository, Mockito.never()).findByCategory(category);
        Mockito.verify(productRepository, Mockito.times(1)).findByCategoryAndNameContaining(category, productName);
        Mockito.verify(modelMapper, Mockito.times(2)).map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
        Assert.assertEquals(list.size(), returnedList.size());

    }

    @Test
    public void uploadData() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", new FileInputStream(new File("src/test/resources/test.csv")));

        Mockito.when(categoryRepository.findByNameEquals(Mockito.anyString())).thenReturn(new Category());
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(new Category());
        Mockito.when(shopRepository.findByNameAndAddress(Mockito.anyString(), Mockito.anyString())).thenReturn(new Shop());
        Mockito.when(shopRepository.save(Mockito.any(Shop.class))).thenReturn(new Shop());
        Mockito.when(productRepository.findByNameEquals(Mockito.anyString())).thenReturn(new Product());
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
        Mockito.when(priceRepository.findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class))).thenReturn(new Price());
        Mockito.when(priceRepository.save(Mockito.any(Price.class))).thenReturn(new Price());

        productService.uploadData(multipartFile);

        Mockito.verify(categoryRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
        Mockito.verify(shopRepository, Mockito.times(2)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(shopRepository, Mockito.never()).save(Mockito.any(Shop.class));
        Mockito.verify(productRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
        Mockito.verify(priceRepository, Mockito.times(2)).findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class));
        Mockito.verify(priceRepository, Mockito.never()).save(Mockito.any(Price.class));
    }

    @Test
    public void uploadDataNewEntities() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", new FileInputStream(new File("src/test/resources/test.csv")));

        Mockito.when(categoryRepository.findByNameEquals(Mockito.anyString())).thenReturn(null);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(new Category());
        Mockito.when(shopRepository.findByNameAndAddress(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Mockito.when(shopRepository.save(Mockito.any(Shop.class))).thenReturn(new Shop());
        Mockito.when(productRepository.findByNameEquals(Mockito.anyString())).thenReturn(null);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
        Mockito.when(priceRepository.findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class))).thenReturn(null);
        Mockito.when(priceRepository.save(Mockito.any(Price.class))).thenReturn(new Price());

        productService.uploadData(multipartFile);

        Mockito.verify(categoryRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.times(2)).save(Mockito.any(Category.class));
        Mockito.verify(shopRepository, Mockito.times(2)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(shopRepository, Mockito.times(2)).save(Mockito.any(Shop.class));
        Mockito.verify(productRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.times(2)).save(Mockito.any(Product.class));
        Mockito.verify(priceRepository, Mockito.times(2)).findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class));
        Mockito.verify(priceRepository, Mockito.times(2)).save(Mockito.any(Price.class));
    }
}