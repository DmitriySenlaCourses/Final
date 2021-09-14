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
