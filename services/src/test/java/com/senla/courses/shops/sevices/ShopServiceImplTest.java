package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.ShopRepository;
import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.ShopDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

public class ShopServiceImplTest {

    @InjectMocks
    private ShopService shopService = new ShopServiceImpl();

    @Mock
    private ShopRepository shopRepository;
    @Mock
    private ModelMapper modelMapper;

    public ShopServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create() {
        Shop shop = new Shop();
        ShopDto shopDto = new ShopDto();
        shopDto.setName("name");
        shopDto.setAddress("address");

        Mockito.when(modelMapper.map(Mockito.any(ShopDto.class), Mockito.eq(Shop.class))).thenReturn(shop);
        Mockito.when(shopRepository.save(shop)).thenReturn(new Shop());
        Mockito.when(modelMapper.map(Mockito.any(Shop.class), Mockito.eq(ShopDto.class))).thenReturn(new ShopDto());
        Mockito.when(shopRepository.findByNameAndAddress(shopDto.getName(), shop.getAddress())).thenReturn(null);

        shopService.create(shopDto);

        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(ShopDto.class), Mockito.eq(Shop.class));
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(Shop.class), Mockito.eq(ShopDto.class));
        Mockito.verify(shopRepository, Mockito.times(1)).save(Mockito.any(Shop.class));
        Mockito.verify(shopRepository, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
    }

    @Test(expected = EntityExistsException.class)
    public void createException() {
        Shop shop = new Shop();
        ShopDto shopDto = new ShopDto();
        shopDto.setName("name");
        shopDto.setAddress("address");

        Mockito.when(modelMapper.map(Mockito.any(ShopDto.class), Mockito.eq(Shop.class))).thenReturn(shop);
        Mockito.when(shopRepository.save(shop)).thenReturn(new Shop());
        Mockito.when(modelMapper.map(Mockito.any(Shop.class), Mockito.eq(ShopDto.class))).thenReturn(new ShopDto());
        Mockito.when(shopRepository.findByNameAndAddress(Mockito.anyString(), Mockito.anyString())).thenReturn(shop);

        shopService.create(shopDto);

        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(), Mockito.any());
        Mockito.verify(shopRepository, Mockito.never()).save(Mockito.any(Shop.class));
        Mockito.verify(shopRepository, Mockito.times(1)).findByNameAndAddress(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getAll() {
        Integer pageNo = 0;
        Integer pageSize = 2;
        String sortBy = "id";
        List<Shop> all = new ArrayList<>();
        all.add(new Shop());
        all.add(new Shop());

        Mockito.when(shopRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(all));
        Mockito.when(modelMapper.map(Mockito.any(Shop.class), Mockito.eq(ShopDto.class))).thenReturn(new ShopDto());

        List<ShopDto> allDto = shopService.getAll(pageNo, pageSize, sortBy);

        Mockito.verify(shopRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
        Mockito.verify(modelMapper, Mockito.times(all.size())).map(Mockito.any(Shop.class), Mockito.eq(ShopDto.class));
        Assert.assertEquals(all.size(), allDto.size());
    }
}