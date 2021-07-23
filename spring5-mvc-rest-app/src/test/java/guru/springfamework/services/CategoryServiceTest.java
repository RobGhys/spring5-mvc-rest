package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    public static final Long ID = 2L;
    public static final String NAME = "Robin";
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Use the CategoryMapper to create an instance of a CategoryServiceImpl
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() throws Exception {
        // Given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        // When
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        // Then
        assertEquals(3, categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() throws Exception {
        // Given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        // When
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        // Then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}