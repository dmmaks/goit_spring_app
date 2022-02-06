package com.edu.goit.service;

import com.edu.goit.dto.KitchenwareDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.exception.BadRequestParamException;
import com.edu.goit.exception.CustomException;
import com.edu.goit.mapper.KitchenwareMapper;
import com.edu.goit.model.Kitchenware;
import com.edu.goit.repository.interfaces.KitchenwareRepository;
import com.edu.goit.service.interfaces.KitchenwareService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Service
public class KitchenwareServiceImpl implements KitchenwareService {
    private final KitchenwareRepository kitchenwareRepository;
    private final KitchenwareMapper kitchenwareMapper;

    @Override
    public Collection<String> getAllCategories() {
        return kitchenwareRepository.getAllCategories();
    }


    @Override
    public KitchenwareDTO getKitchenwareById(Long id) {
        try {
            return kitchenwareMapper.kitchenwaretoKitchenwareDTO(kitchenwareRepository.findById(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
        }
    }

    @Override
    @Transactional
    public KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO) {
        try {
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
            long id = kitchenwareRepository.create(kitchenware);
            kitchenwareDTO.setId(String.valueOf(id));
            kitchenwareDTO.setActive(true);
            return kitchenwareDTO;

        } catch (
                DataIntegrityViolationException ex) {
            throw new BadRequestParamException("category", "Category is invalid");
        }
    }

    @Override
    @Transactional
    public KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO, long id) {
        try {
            kitchenwareDTO.setId(String.valueOf(id));
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
            boolean updated = kitchenwareRepository.update(kitchenware);
            if (!updated) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
            }
            return kitchenwareDTO;
        } catch (
                DataIntegrityViolationException ex) {
            throw new BadRequestParamException("category", "Category is invalid");
        }
    }

    @Override
    @Transactional
    public void changeKitchenwareStatus(long id) {
        boolean updated = kitchenwareRepository.changeStatusById(id);
        if (!updated) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
        }
    }

    @Override
    public PaginationDTO<KitchenwareDTO> getFilteredKitchenware(String name, Collection<String> args, Boolean active, int limit, boolean order, int currentPage) {
        int count = kitchenwareRepository.countFilteredKitchenware(name, args, active);
        Collection<Kitchenware> kitchenwareCollection = kitchenwareRepository.filterKitchenware(
                name, args, active, limit, currentPage * limit, order
        );
        return new PaginationDTO<>(
                kitchenwareMapper.kitchenwarePageToDtoCollection(kitchenwareCollection), count
        );
    }
}
