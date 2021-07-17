package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.CategoryService;
import com.senla.courses.shops.dao.CategoryRepository;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.dto.CategoryDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;

    public CategoryServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll() {
        List<Category> all = new ArrayList<>();
        all.add(new Category());
        all.add(new Category());

        Mockito.when(categoryRepository.findAll()).thenReturn(all);
        Mockito.when(modelMapper.map(Mockito.any(Category.class), Mockito.eq(CategoryDto.class))).thenReturn(new CategoryDto());

        List<CategoryDto> allDto = categoryService.getAll();

        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
        Mockito.verify(modelMapper, Mockito.times(all.size())).map(Mockito.any(Category.class), Mockito.eq(CategoryDto.class));
        Assert.assertEquals(all.size(), allDto.size());

    }
}