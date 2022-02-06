package com.edu.goit.service.interfaces;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.dto.PaginationDTO;

public interface ManufacturerService {
    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);

    ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO, long id);

    void deleteManufacturer(long id);

    ManufacturerDTO getManufacturerById(Long id);

    PaginationDTO<ManufacturerDTO> getFilteredManufacturers(String name, int limit, boolean order, int currentPage);
}
