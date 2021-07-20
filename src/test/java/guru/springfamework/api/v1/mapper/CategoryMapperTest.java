package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public static final long ID = 1L;
    public static final String MY_NAME = "Rob";


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDTO() throws Exception {
        // Given
        Category category = new Category();
        category.setName(MY_NAME);
        category.setId(ID);

        // When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // Then
        assertEquals(Long.valueOf(1L), categoryDTO.getId());
        assertEquals(MY_NAME, categoryDTO.getName());
    }
}