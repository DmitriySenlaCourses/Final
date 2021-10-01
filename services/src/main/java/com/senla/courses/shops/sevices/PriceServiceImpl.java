package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.api.services.ProductService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.PriceRepository;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PriceService} interface
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private PriceRepository priceRepository;
    private ProductService productService;
    private ShopService shopService;
    private ModelMapper modelMapper;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, ModelMapper modelMapper,
                            ProductService productService, ShopService shopService) {
        this.priceRepository = priceRepository;
        this.productService = productService;
        this.shopService = shopService;
        this.modelMapper = modelMapper;
    }

    public PriceServiceImpl() {
    }

    @Override
    public Map<LocalDate, BigDecimal> getDynamics(String productName, String shopName, String shopAddress, String start, String end, Integer pageNo, Integer pageSize, String sortBy) {
        Map<LocalDate, BigDecimal> map = new LinkedHashMap<>();
        Product product = productService.findByName(productName);
        Shop shop = shopService.findByNameAndAddress(shopName, shopAddress);
        if (product == null) {
            throw new EntityNotFoundException(String.format("Product %s not found", productName));
        }
        if (shop == null) {
            throw new EntityNotFoundException(String.format("Shop %s, %s not found", shopName, shopAddress));
        }
        LocalDate startDate;
        LocalDate endDate;
        startDate = start == null ? LocalDate.MIN : convertDate(start);
        endDate = end == null ? LocalDate.now() : convertDate(end);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Price> prices = priceRepository.findByProductAndShopsAndDateBetween(product, shop, startDate, endDate, pageable);
        for (Price price : prices) {
            map.put(price.getDate(), price.getValue());
        }
        return map;
    }

    @Override
    public List<PriceShop> getLastPrices(String productName, Integer pageNo, Integer pageSize, String sortBy) {
        Product product = productService.findByName(productName);
        if (product == null) {
            throw new EntityNotFoundException(String.format("Product %s not found", productName));
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<PriceShop> lastPrices = priceRepository.getLastPrices(product.getId(), pageable);
        return lastPrices;
    }

    @Override
    public Price findByProductAndShopsAndDate(Product product, Shop shop, LocalDate date) {
        return priceRepository.findByProductAndShopsAndDate(product, shop, date);
    }

    @Override
    public Price save(Price price) {
        return priceRepository.save(price);
    }

    private LocalDate convertDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
