package com.edu.goit.mapper;

import com.edu.goit.dto.ProductDTO;
import com.edu.goit.model.Manufacturer;
import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(target="id", source="productDTO.id"),
            @Mapping(target="name", source="productDTO.name"),
            @Mapping(target="price", source="productDTO.price"),
            @Mapping(target="manufacturerId", source="productDTO.manufacturerId")
    })
    Product productDTOToProduct(ProductDTO productDTO);
    @Mappings({
            @Mapping(target="id", source="product.id"),
            @Mapping(target="name", source="product.name"),
            @Mapping(target="price", source="product.price"),
            @Mapping(target="manufacturerId", source="product.manufacturerId")
    })
    ProductDTO productToProductDTO(Product product);

    @Mappings({
            @Mapping(target="id", source="productPage.id"),
            @Mapping(target="name", source="productPage.name"),
            @Mapping(target="price", source="productPage.price"),
            @Mapping(target="manufacturerId", source="productPage.manufacturerId")
    })
    Collection<ProductDTO> productPageToDtoCollection (Collection<Product> productPage);
}