package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {
    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    public static final String NAME = "Amazon";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void vendorToVendorDTO() throws Exception {
        // Given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        // When
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // Then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        // Then
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        // When
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        // Then
        assertEquals(NAME, vendor.getName());
    }
}