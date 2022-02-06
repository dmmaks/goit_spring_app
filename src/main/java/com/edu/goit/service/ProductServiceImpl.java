package com.edu.goit.service;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.ProductDTO;
import com.edu.goit.exception.BadRequestParamException;
import com.edu.goit.exception.CustomException;
import com.edu.goit.mapper.ProductMapper;
import com.edu.goit.model.Product;
import com.edu.goit.repository.interfaces.ProductRepository;
import com.edu.goit.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO getProductById(Long id) {
        try {
            return productMapper.productToProductDTO(productRepository.findById(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Product with id %s not found.", id));
        }
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {


            Product product = productMapper.productDTOToProduct(productDTO);
            long id = productRepository.create(product);
            productDTO.setId(String.valueOf(id));
            return productDTO;

        } catch (
                DataIntegrityViolationException ex) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Manufacturer id is invalid");
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, long id) {
        productDTO.setId(String.valueOf(id));
        Product product = productMapper.productDTOToProduct(productDTO);
        boolean updated = productRepository.update(product);
        if (!updated) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Product with id %s not found.", id));
        }
        return productDTO;
    }

    @Override
    public void deleteProduct(long id) {
        boolean deleted = productRepository.deleteById(id);
        if (!deleted) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Product with id %s not found.", id));
        }
    }

    @Override
    public PaginationDTO<ProductDTO> getFilteredProducts(String name, Collection<String> manufacturers, int limit, boolean order, int currentPage) {
        Collection<Long> numericManufacturers = null;
        if (manufacturers != null) {
            numericManufacturers = manufacturers.stream().map(Long::parseLong).collect(Collectors.toList());
        }
        int count = productRepository.countFilteredProducts(name, numericManufacturers);
        Collection<Product> productCollection = productRepository.filterProducts(
                name, numericManufacturers, limit, currentPage * limit, order
        );
        return new PaginationDTO<>(
                productMapper.productPageToDtoCollection(productCollection), count
        );
    }
}
