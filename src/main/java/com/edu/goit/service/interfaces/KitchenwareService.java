package com.edu.goit.service.interfaces;

import com.edu.goit.dto.KitchenwareDTO;
import com.edu.goit.dto.PaginationDTO;

import java.util.Collection;

public interface KitchenwareService {
    Collection<String> getAllCategories();

    KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO);

    KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO, long id);

    void changeKitchenwareStatus(long id);

    KitchenwareDTO getKitchenwareById(Long id);

    PaginationDTO<KitchenwareDTO> getFilteredKitchenware(String name, Collection<String> args, Boolean active, int limit, boolean order, int currentPage);
}
