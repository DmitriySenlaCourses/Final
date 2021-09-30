package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.api.services.UploadService;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UploadServiceImplTest {

    @InjectMocks
    private UploadService uploadService = new UploadServiceImpl();

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ShopService shopService;
    @Mock
    private PriceService priceService;

    public UploadServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void uploadData() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", new FileInputStream(new File("src/test/resources/test.csv")));
        Shop shop = new Shop();
        Set<Shop> shops = new HashSet<>();
        shops.add(shop);
        Product product = new Product();
        product.setShops(shops);

        Mockito.when(categoryService.findByName(Mockito.anyString())).thenReturn(new Category());
        Mockito.when(categoryService.save(Mockito.any(Category.class))).thenReturn(new Category());
        Mockito.when(shopService.findByNameAndAddress(Mockito.anyString(), Mockito.anyString())).thenReturn(shop);
        Mockito.when(shopService.save(Mockito.any(Shop.class))).thenReturn(shop);
        Mockito.when(productRepository.findByNameEquals(Mockito.anyString())).thenReturn(product);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(priceService.findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class))).thenReturn(new Price());
        Mockito.when(priceService.save(Mockito.any(Price.class))).thenReturn(new Price());

        uploadService.uploadData(multipartFile);

        Mockito.verify(categoryService, Mockito.times(2)).findByName(Mockito.anyString());
        Mockito.verify(categoryService, Mockito.never()).save(Mockito.any(Category.class));
        Mockito.verify(shopService, Mockito.times(2)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(shopService, Mockito.never()).save(Mockito.any(Shop.class));
        Mockito.verify(productRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.times(2)).save(Mockito.any(Product.class));
        Mockito.verify(priceService, Mockito.times(2)).findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class));
        Mockito.verify(priceService, Mockito.never()).save(Mockito.any(Price.class));
    }

    @Test
    public void uploadDataNewEntities() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", new FileInputStream(new File("src/test/resources/test.csv")));

        Mockito.when(categoryService.findByName(Mockito.anyString())).thenReturn(null);
        Mockito.when(categoryService.save(Mockito.any(Category.class))).thenReturn(new Category());
        Mockito.when(shopService.findByNameAndAddress(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Mockito.when(shopService.save(Mockito.any(Shop.class))).thenReturn(new Shop());
        Mockito.when(productRepository.findByNameEquals(Mockito.anyString())).thenReturn(null);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
        Mockito.when(priceService.findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class))).thenReturn(null);
        Mockito.when(priceService.save(Mockito.any(Price.class))).thenReturn(new Price());

        uploadService.uploadData(multipartFile);

        Mockito.verify(categoryService, Mockito.times(2)).findByName(Mockito.anyString());
        Mockito.verify(categoryService, Mockito.times(2)).save(Mockito.any(Category.class));
        Mockito.verify(shopService, Mockito.times(2)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(shopService, Mockito.times(2)).save(Mockito.any(Shop.class));
        Mockito.verify(productRepository, Mockito.times(2)).findByNameEquals(Mockito.anyString());
        Mockito.verify(productRepository, Mockito.times(2)).save(Mockito.any(Product.class));
        Mockito.verify(priceService, Mockito.times(2)).findByProductAndShopsAndDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class));
        Mockito.verify(priceService, Mockito.times(2)).save(Mockito.any(Price.class));
    }
}
