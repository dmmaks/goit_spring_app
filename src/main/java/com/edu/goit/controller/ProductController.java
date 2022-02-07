package com.edu.goit.controller;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.ProductDTO;
import com.edu.goit.service.interfaces.ManufacturerService;
import com.edu.goit.service.interfaces.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product has been added"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public ProductDTO createProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO dto = productService.createProduct(productDTO);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Parameter(s) is/are invalid"),
            @ApiResponse(code = 404, message = "Item not found")})
    public ProductDTO updateProduct(@PathVariable long id, @RequestBody @Valid ProductDTO productDTO) {
        ProductDTO dto = productService.updateProduct(productDTO, id);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public ProductDTO getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")})
    public PaginationDTO<ProductDTO> getFilteredProducts(
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) @Min(value = 0, message = "Current page must be higher than 0") int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "manufacturers", required = false) Collection<@Pattern(regexp = "[0-9]+", message = "manufacturers should be numeric") String> manufacturers,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order) {
        return productService.getFilteredProducts(name, manufacturers, pageSize, order, currentPage);
    }
}