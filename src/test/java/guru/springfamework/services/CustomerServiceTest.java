package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Robin";
    public static final String LAST_NAME = "Ghyselinck";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

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
    public void getCustomerByFirstName() throws Exception {
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
}