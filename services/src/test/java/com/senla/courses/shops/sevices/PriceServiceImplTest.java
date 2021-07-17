package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.PriceService;
import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.dao.PriceRepository;
import com.senla.courses.shops.dao.ProductRepository;
import com.senla.courses.shops.dao.ShopRepository;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    private ProductRepository productRepository;
    @Mock
    private ShopRepository shopRepository;

    public PriceServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getDynamics() {
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productRepository.findByNameEquals(productName)).thenReturn(new Product());
        Mockito.when(shopRepository.findByNameAndAddress(shopName, shopAddress)).thenReturn(new Shop());
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(shopRepository, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.times(1)).findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDynamicsProductException() {
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productRepository.findByNameEquals(productName)).thenReturn(null);
        Mockito.when(shopRepository.findByNameAndAddress(shopName, shopAddress)).thenReturn(new Shop());
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(shopRepository, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.never()).findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDynamicsShopException() {
        String productName = "Milk";
        String shopName = "Shop";
        String shopAddress = "Address";
        String start = "2021-06-15";
        String end = "2021-07-15";

        List<Price> prices = new ArrayList<>();

        Mockito.when(productRepository.findByNameEquals(productName)).thenReturn(new Product());
        Mockito.when(shopRepository.findByNameAndAddress(shopName, shopAddress)).thenReturn(null);
        Mockito.when(priceRepository.findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(prices);

        Map<LocalDate, BigDecimal> dynamics = priceService.getDynamics(productName, shopName, shopAddress, start, end);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(Mockito.anyString());
        Mockito.verify(shopRepository, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(priceRepository, Mockito.never()).findByProductAndShopsAndDateBetweenOrderByDate(Mockito.any(Product.class), Mockito.any(Shop.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
    }

    @Test
    public void getLastPrices() {
        String productName = "Milk";
        Product product = new Product();
        product.setId(1L);
        List<PriceShop> priceShops = new ArrayList<>();

        Mockito.when(productRepository.findByNameEquals(productName)).thenReturn(product);
        Mockito.when(priceRepository.getLastPrices(1L)).thenReturn(priceShops);

        priceService.getLastPrices(productName);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(productName);
        Mockito.verify(priceRepository, Mockito.times(1)).getLastPrices(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getLastPricesException() {
        String productName = "Milk";
        Product product = new Product();
        product.setId(1L);
        List<PriceShop> priceShops = new ArrayList<>();

        Mockito.when(productRepository.findByNameEquals(productName)).thenReturn(null);
        Mockito.when(priceRepository.getLastPrices(1L)).thenReturn(priceShops);

        priceService.getLastPrices(productName);

        Mockito.verify(productRepository, Mockito.times(1)).findByNameEquals(productName);
        Mockito.verify(priceRepository, Mockito.never()).getLastPrices(1L);
    }
}