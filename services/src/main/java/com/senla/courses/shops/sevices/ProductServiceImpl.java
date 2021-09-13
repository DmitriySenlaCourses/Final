package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.ProductService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.model.AllEntities;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.ProductDto;
import com.senla.courses.shops.sevices.exceptions.ReadFileException;
import com.senla.courses.shops.sevices.util.CsvLineToAllEntities;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ProductService} interface
 */
@Service
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;
    private ShopService shopService;
    private PriceService priceService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService,
                              ShopService shopService, PriceService priceService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.priceService = priceService;
        this.shopService = shopService;
    }

    public ProductServiceImpl() {
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        if (isProductExists(productDto)) {
            throw new EntityExistsException(String.format("Product %s already exists", productDto.getName()));
        }
        Product product = modelMapper.map(productDto, Product.class);
        Category category = getCategory(productDto);
        product.setCategory(category);
        Product returnedProduct = productRepository.save(product);
        log.info(String.format("Create product %s", returnedProduct.getName()));
        return modelMapper.map(returnedProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        if (isProductExists(productDto)) {
            throw new EntityExistsException(String.format("Product %s already exists", productDto.getName()));
        }
        Product one = productRepository.getOne(productDto.getId());
        one.setName(productDto.getName());
        one.setUnit(productDto.getUnit());
        Category category = getCategory(productDto);
        one.setCategory(category);
        productRepository.save(one);
        log.info(String.format("Update product %s", one.getName()));
        return modelMapper.map(one, ProductDto.class);
    }

    @Override
    public void delete(long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new EntityNotFoundException(String.format("Product id = %s not found", id));
        }
        productRepository.deleteById(id);
        log.info(String.format("Delete product %s", product.getName()));
    }

    @Override
    public List<ProductDto> findByCategoryAndName(String categoryName, String productName) {
        Category foundCategory = categoryService.findByName(categoryName);
        List<Product> list = null;

        if (foundCategory == null) {
            throw new EntityNotFoundException(String.format("%s category not found", categoryName));
        }

        if (productName == null) {
            list = productRepository.findByCategory(foundCategory);
        } else {
            list = productRepository.findByCategoryAndNameContaining(foundCategory, productName);
        }

        return list.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByNameEquals(name);
    }

    @Override
    public void uploadData(MultipartFile file) {

        List<AllEntities> list = csvToEntities(file);

        for (AllEntities allEntities : list) {
            Category category = allEntities.getCategory();
            Price price = allEntities.getPrice();
            Product product = allEntities.getProduct();
            Shop shop = allEntities.getShop();

            Category categoryFromDb = categoryService.findByName(category.getName());
            if (categoryFromDb == null) {
                category = categoryService.save(category);
                log.info(String.format("Create category %s", category.getName()));
            } else {
                category = categoryFromDb;
            }

            Shop shopFromDb = shopService.findByNameAndAddress(shop.getName(), shop.getAddress());
            if (shopFromDb == null) {
                shop = shopService.save(shop);
                log.info(String.format("Create shop %s", shop.getName()));
            } else {
                shop = shopFromDb;
            }

            Product productFromDb = productRepository.findByNameEquals(product.getName());
            if (productFromDb == null) {
                product.setCategory(category);
                Set<Shop> shops = new HashSet<>();
                shops.add(shop);
                product.setShops(shops);
                product = productRepository.save(product);
                log.info(String.format("Create product %s", product.getName()));
            } else  {
                product = productFromDb;
            }

            Price priceFromDb = priceService.findByProductAndShopsAndDate(product, shop, price.getDate());
            if (priceFromDb == null) {
                price.setProduct(product);
                Set<Shop> shops = new HashSet<>();
                shops.add(shop);
                price.setShops(shops);
                price = priceService.save(price);
                log.info(String.format("Create price %s, %s", price.getDate(), price.getValue()));
            } else {
                price = priceFromDb;
            }
        }
    }

    private Category getCategory(ProductDto productDto) {
        Category category = categoryService.findByName(productDto.getCategory().getName());
        if (category == null) {
            category = new Category();
            category.setName(productDto.getCategory().getName());
            categoryService.save(category);
        }
        return category;
    }

    private List<AllEntities> csvToEntities(MultipartFile file) {
        List<AllEntities> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while (bufferedReader.ready()) {
                String csvLine = bufferedReader.readLine();
                String[] data = csvLine.split(";");
                AllEntities entities = CsvLineToAllEntities.convert(data);
                list.add(entities);
            }
        } catch (IOException e) {
            throw new ReadFileException(String.format("File read error. File %s", file.getOriginalFilename()), e);
        }
        return list;
    }

    private boolean isProductExists(ProductDto productDto) {
        return productRepository.findByNameEquals(productDto.getName()) != null;
    }
}
