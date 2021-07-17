package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.dao.CategoryRepository;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link CategoryService} interface
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryServiceImpl() {
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
