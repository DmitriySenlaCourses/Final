package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.dao.PriceRepository;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.dao.ShopRepository;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProductRepository productRepository;
    private ShopRepository shopRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, ModelMapper modelMapper,
                            ProductRepository productRepository, ShopRepository shopRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }

    public PriceServiceImpl() {
    }

    @Override
    public Map<LocalDate, BigDecimal> getDynamics(String productName, String shopName, String shopAddress, String start, String end) {
        Map<LocalDate, BigDecimal> map = new LinkedHashMap<>();
        Product product = productRepository.findByNameEquals(productName);
        Shop shop = shopRepository.findByNameAndAddress(shopName, shopAddress);
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

        List<Price> prices = priceRepository.findByProductAndShopsAndDateBetweenOrderByDate(product, shop, startDate, endDate);
        for (Price price : prices) {
            map.put(price.getDate(), price.getValue());
        }
        return map;
    }

    @Override
    public List<PriceShop> getLastPrices(String productName) {
        Product product = productRepository.findByNameEquals(productName);
        if (product == null) {
            throw new EntityNotFoundException(String.format("Product %s not found", productName));
        }
        List<PriceShop> lastPrices = priceRepository.getLastPrices(product.getId());
        return lastPrices;
    }

    private LocalDate convertDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
