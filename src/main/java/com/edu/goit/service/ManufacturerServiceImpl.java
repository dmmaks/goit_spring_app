package com.edu.goit.service;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.exception.CustomException;
import com.edu.goit.mapper.ManufacturerMapper;
import com.edu.goit.model.Manufacturer;
import com.edu.goit.repository.interfaces.ManufacturerRepository;
import com.edu.goit.service.interfaces.ManufacturerService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerDTO getManufacturerById(Long id) {
        try {
            return manufacturerMapper.manufacturerToManufacturerDTO(manufacturerRepository.findById(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Manufacturer with id %s not found.", id));
        }
    }

    @Override
    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = manufacturerMapper.manufacturerDTOToManufacturer(manufacturerDTO);
        long id = manufacturerRepository.create(manufacturer);
        manufacturerDTO.setId(String.valueOf(id));
        return manufacturerDTO;
    }

    @Override
    public ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO, long id) {
            manufacturerDTO.setId(String.valueOf(id));
            Manufacturer manufacturer = manufacturerMapper.manufacturerDTOToManufacturer(manufacturerDTO);
            boolean updated = manufacturerRepository.update(manufacturer);
            if (!updated) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Manufacturer with id %s not found.", id));
            }
            return manufacturerDTO;
    }

    @Override
    public void deleteManufacturer(long id) {
        boolean deleted = manufacturerRepository.deleteById(id);
        if (!deleted) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Manufacturer with id %s not found.", id));
        }
    }

    @Override
    public PaginationDTO<ManufacturerDTO> getFilteredManufacturers(String name, int limit, boolean order, int currentPage) {
        int count = manufacturerRepository.countFilteredManufacturers(name);
        Collection<Manufacturer> manufacturerCollection = manufacturerRepository.filterManufacturers(
                name, limit, currentPage * limit, order
        );
        return new PaginationDTO<>(
                manufacturerMapper.manufacturerPageToDtoCollection(manufacturerCollection), count
        );
    }
}
