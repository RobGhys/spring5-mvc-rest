package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(vendor ->
                {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    // Helper method
    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO ->
                {
                    vendorDTO.setVendorUrl(getVendorUrl(id));

                    return vendorDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    // Helper method
    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnDTO;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository
                .findById(id)
                .map(vendor ->
                {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }

                    VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                    returnDTO.setVendorUrl(getVendorUrl(id));

                    return returnDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
