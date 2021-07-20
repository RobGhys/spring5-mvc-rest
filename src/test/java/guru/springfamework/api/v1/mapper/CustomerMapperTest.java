package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Robin";
    public static final String LAST_NAME = "Ghys";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void customerToCustomerDTO() throws Exception {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        // When
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // Then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }
}