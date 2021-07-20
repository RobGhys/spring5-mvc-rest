package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {
    public static final String NAME_1 = "Robin";
    public static final Long ID_1 = 1L;
    public static final String NAME_2 = "Jean";
    public static final Long ID_2 = 2L;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        // Given

        // --> Setup 2 instances of CategoryDTO
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(ID_1);
        categoryDTO1.setName(NAME_1);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(ID_2);
        categoryDTO2.setName(NAME_2);

        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);

        // When
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Then
        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        // Given

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID_1);
        categoryDTO.setName(NAME_1);

        // When
        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        // Then
        mockMvc.perform(get(CategoryController.BASE_URL + "/Robin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }
}