package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
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
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    public static final String LAST_NAME_1 = "Ghys";
    public static final String FIRST_NAME_1 = "Robin";
    public static final Long ID_1 = 1L;
    public static final String LAST_NAME_2 = "Dupont";
    public static final String FIRST_NAME_2 = "Jean";
    public static final Long ID_2 = 2L;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {
        // Given
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(ID_1);
        customerDTO1.setLastName(LAST_NAME_1);
        customerDTO1.setFirstName(FIRST_NAME_1);

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(ID_2);
        customerDTO2.setLastName(LAST_NAME_2);
        customerDTO2.setFirstName(FIRST_NAME_2);

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);

        // When
        when(customerService.getAllCustomers()).thenReturn(customers);

        // Then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerByFirstName() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID_1);
        customerDTO.setLastName(LAST_NAME_1);
        customerDTO.setFirstName(FIRST_NAME_1);

        // When
        when(customerService.getCustomerByFirstName(anyString())).thenReturn(customerDTO);

        // Then
        mockMvc.perform(get("/api/v1/customers/Robin/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_1)));
    }
}