package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.api.services.ProductService;
import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.PriceRepository;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceServiceImplTest {

    @InjectMocks
    private PriceService priceService = new PriceServiceImpl();

    @Mock
    private PriceRepository priceRepository;
    @Mock
    private ProductService productService;
    @Mock
    private ShopService shopService;

    public PriceServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getDynamics() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productService.findByName(productName)).thenReturn(new Product());
        Mockito.when(shopService.findByNameAndAddress(shopName, shopAddress)).thenReturn(new Shop());
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end, pageNo, pageSize, sortBy);

        Mockito.verify(productService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(shopService, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.times(1)).findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDynamicsProductException() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productService.findByName(productName)).thenReturn(null);
        Mockito.when(shopService.findByNameAndAddress(shopName, shopAddress)).thenReturn(new Shop());
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end, pageNo, pageSize, sortBy);

        Mockito.verify(productService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(shopService, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.never()).findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDynamicsShopException() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productService.findByName(productName)).thenReturn(new Product());
        Mockito.when(shopService.findByNameAndAddress(shopName, shopAddress)).thenReturn(null);
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end, pageNo, pageSize, sortBy);

        Mockito.verify(productService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(shopService, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.never()).findByProductAndShopsAndDateBetween(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(PageRequest.class));
    }

    @Test
    public void getLastPrices() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        String productName = "Milk";
        Product product = new Product();
        product.setId(1L);
        List<PriceShop> priceShops = new ArrayList<>();

        Mockito.when(productService.findByName(productName)).thenReturn(product);
        Mockito.when(priceRepository.getLastPrices(1L, pageable)).thenReturn(priceShops);

        priceService.getLastPrices(productName, pageNo, pageSize, sortBy);

        Mockito.verify(productService, Mockito.times(1)).findByName(productName);
        Mockito.verify(priceRepository, Mockito.times(1)).getLastPrices(1L, pageable);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getLastPricesException() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        String productName = "Milk";
        Product product = new Product();
        product.setId(1L);
        List<PriceShop> priceShops = new ArrayList<>();

        Mockito.when(productService.findByName(productName)).thenReturn(null);
        Mockito.when(priceRepository.getLastPrices(1L, pageable)).thenReturn(priceShops);

        priceService.getLastPrices(productName, pageNo, pageSize, sortBy);

        Mockito.verify(productService, Mockito.times(1)).findByName(productName);
        Mockito.verify(priceRepository, Mockito.never()).getLastPrices(1L, pageable);
    }
}