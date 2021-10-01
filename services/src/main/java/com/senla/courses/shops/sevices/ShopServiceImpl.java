package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.ShopService;
import com.senla.courses.shops.dao.ShopRepository;
import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.ShopDto;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ShopService} interface
 */
@Service
@Transactional
@Log4j2
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }

    public ShopServiceImpl() {
    }

    @Override
    public ShopDto create(ShopDto shopDto) {
        if (isShopExists(shopDto)) {
            throw new EntityExistsException(String.format("Shop %s, %s already exists", shopDto.getName(), shopDto.getAddress()));
        }
        Shop shop = modelMapper.map(shopDto, Shop.class);
        Shop returnedShop = shopRepository.save(shop);
        log.info(String.format("Create shop %s", shop.getName()));
        return modelMapper.map(returnedShop, ShopDto.class);
    }

    @Override
    public List<ShopDto> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Shop> shopPage = shopRepository.findAll(pageable);
        if (shopPage.hasContent()) {
            return shopPage.stream().map(shop -> modelMapper.map(shop, ShopDto.class)).collect(Collectors.toList());
        } else {
            return new ArrayList<ShopDto>();
        }
    }

    @Override
    public Shop findByNameAndAddress(String name, String address) {
        return shopRepository.findByNameAndAddress(name, address);
    }

    @Override
    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    private boolean isShopExists(ShopDto shopDto) {
        return shopRepository.findByNameAndAddress(shopDto.getName(), shopDto.getAddress()) != null;
    }
}
