package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.dao.CategoryRepository;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<CategoryDto> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        if (categoryPage.hasContent()) {
            return categoryPage.getContent().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        } else {
            return new ArrayList<CategoryDto>();
        }
//        return all.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByNameEquals(name);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
