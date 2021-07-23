package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CustomerServiceTest {
    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Robin";
    public static final String LAST_NAME = "Ghyselinck";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        // Given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());

        // When
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // Then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        // When
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        // Then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    public void createNewCustomerTest() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Camille");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        // When
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        // Then
        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTOTest() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        // When
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        // Then
        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void deleteCustomerByIdTest() throws Exception {
        // Given
        Long id = 1L;
        customerRepository.deleteById(id);

        // Then
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}