package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
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

public class VendorServiceImplTest {
    public static final Long ID = 1L;
    public static final String NAME = "Amazon";

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendorsTest() {
        // Given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        // When
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        // Then
        assertEquals(2, vendorDTOS.size());
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        // Given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        // When
        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        // Then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void createNewVendorTest() throws Exception {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("eBay");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        // When
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        // Then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTOTest() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("eBay");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        // When
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        VendorDTO savedDTO = vendorService.saveVendorByDTO(1L, vendorDTO);

        // Then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDTO.getVendorUrl());
    }

    @Test
    public void deleteVendorByIdTest() throws Exception {
        // Given
        Long id = 1L;
        vendorRepository.deleteById(id);

        // Then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}