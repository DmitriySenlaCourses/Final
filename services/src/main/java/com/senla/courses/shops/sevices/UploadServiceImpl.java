package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.api.services.UploadService;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.model.*;
import com.senla.courses.shops.sevices.exceptions.ReadFileException;
import com.senla.courses.shops.sevices.util.CsvLineToAllEntities;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Log4j2
public class UploadServiceImpl implements UploadService {

    private ProductRepository productRepository;
    private CategoryService categoryService;
    private ShopService shopService;
    private PriceService priceService;

    @Autowired
    public UploadServiceImpl(ProductRepository productRepository, CategoryService categoryService, ShopService shopService, PriceService priceService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.shopService = shopService;
        this.priceService = priceService;
    }

    public UploadServiceImpl() {
    }

    @Override
    public void uploadData(MultipartFile file) {

        List<AllEntities> list = csvToEntities(file);

        for (AllEntities allEntities : list) {
            Category category = allEntities.getCategory();
            Price price = allEntities.getPrice();
            Product product = allEntities.getProduct();
            Shop shop = allEntities.getShop();

            category = ifNotExistsSaveCategory(category);
            shop = ifNotExistsSaveShop(shop);
            product = ifNotExistsSaveProduct(product, category, shop);
            price = ifNotExistsSavePrice(price, product, shop);
        }
    }

    private Category ifNotExistsSaveCategory(Category category) {
        Category categoryFromDb = categoryService.findByName(category.getName());
        Category returnedCategory;
        if (categoryFromDb == null) {
            returnedCategory = categoryService.save(category);
            log.info(String.format("Create category %s", category.getName()));
        } else {
            returnedCategory = categoryFromDb;
        }
        return returnedCategory;
    }

    private Shop ifNotExistsSaveShop(Shop shop) {
        Shop shopFromDb = shopService.findByNameAndAddress(shop.getName(), shop.getAddress());
        Shop returnedShop;
        if (shopFromDb == null) {
            returnedShop = shopService.save(shop);
            log.info(String.format("Create shop %s", shop.getName()));
        } else {
            returnedShop = shopFromDb;
        }
        return returnedShop;
    }

    private Product ifNotExistsSaveProduct(Product product, Category category, Shop shop) {
        Product productFromDb = productRepository.findByNameEquals(product.getName());
        Product returnedProduct;
        if (productFromDb == null) {
            product.setCategory(category);
            Set<Shop> shops = new HashSet<>();
            shops.add(shop);
            product.setShops(shops);
            returnedProduct = productRepository.save(product);
            log.info(String.format("Create product %s", product.getName()));
        } else  {
            Set<Shop> shops = productFromDb.getShops();
            shops.add(shop);
            returnedProduct = productRepository.save(productFromDb);
        }
        return returnedProduct;
    }

    private Price ifNotExistsSavePrice(Price price, Product product, Shop shop) {
        Price priceFromDb = priceService.findByProductAndShopsAndDate(product, shop, price.getDate());
        Price returnedPrice;
        if (priceFromDb == null) {
            price.setProduct(product);
            Set<Shop> shops = new HashSet<>();
            shops.add(shop);
            price.setShops(shops);
            returnedPrice = priceService.save(price);
            log.info(String.format("Create price %s, %s", price.getDate(), price.getValue()));
        } else {
            returnedPrice = priceFromDb;
        }
        return returnedPrice;
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
}
