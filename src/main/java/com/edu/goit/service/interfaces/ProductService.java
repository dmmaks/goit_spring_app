package com.edu.goit.service.interfaces;

import com.edu.goit.dto.ProductDTO;
import com.edu.goit.dto.PaginationDTO;

import java.util.Collection;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO, long id);

    void deleteProduct(long id);

    ProductDTO getProductById(Long id);

    PaginationDTO<ProductDTO> getFilteredProducts(String name, Collection<String> manufacturers, int limit, boolean order, int currentPage);
}
