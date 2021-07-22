package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
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

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {
    public static final String NAME_1 = "Amazon";
    public static final String NAME_2 = "Facebook";
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;

    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void listCustomersTest() throws Exception {
        // Given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(NAME_1);
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName(NAME_2);
        vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");

        List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);

        // When
        when(vendorService.getAllVendors()).thenReturn(vendors);

        // Then
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        // Given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(NAME_1);
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        // When
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO1);

        // Then
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }

    @Test
    public void createNewVendorTest() throws Exception {
        // Given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(NAME_1);
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO1.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        // When
        when(vendorService.createNewVendor(vendorDTO1)).thenReturn(returnDTO);

        // Then
        mockMvc.perform(post(VendorController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Amazon")))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void updateVendorTest() throws Exception {
        // Given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME_1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        // When
        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // Then
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Amazon")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void patchVendorTest() throws Exception {
        // Given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME_1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        // When
        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // Then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Amazon")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void deleteVendorTest() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}