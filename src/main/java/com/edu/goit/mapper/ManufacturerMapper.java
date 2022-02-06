package com.edu.goit.mapper;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.model.Manufacturer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Mapper(componentModel = "spring")
public interface ManufacturerMapper {
    @Mappings({
            @Mapping(target="id", source="manufacturerDTO.id"),
            @Mapping(target="name", source="manufacturerDTO.name")
    })
    Manufacturer manufacturerDTOToManufacturer(ManufacturerDTO manufacturerDTO);
    @Mappings({
            @Mapping(target="id", source="manufacturer.id"),
            @Mapping(target="name", source="manufacturer.name")
    })
    ManufacturerDTO manufacturerToManufacturerDTO(Manufacturer manufacturer);

    @Mappings({
            @Mapping(target="id", source="manufacturerPage.id"),
            @Mapping(target="name", source="manufacturerPage.name")
    })
    Collection<ManufacturerDTO> manufacturerPageToDtoCollection (Collection<Manufacturer> manufacturerPage);
}