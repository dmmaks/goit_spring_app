package com.edu.goit.controller;

import com.edu.goit.dto.ManufacturerDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.service.interfaces.ManufacturerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Manufacturer has been added"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    public ManufacturerDTO createManufacturer(@RequestBody @Valid ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO dto = manufacturerService.createManufacturer(manufacturerDTO);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Parameter(s) is/are invalid"),
            @ApiResponse(code = 404, message = "Item not found")})
    public ManufacturerDTO updateManufacturer(@PathVariable long id, @RequestBody @Valid ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO dto = manufacturerService.updateManufacturer(manufacturerDTO, id);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping(value = "/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public void deleteManufacturer(@PathVariable long id) {
        manufacturerService.deleteManufacturer(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public ManufacturerDTO getManufacturerById(@PathVariable long id) {
        return manufacturerService.getManufacturerById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")})
    public PaginationDTO<ManufacturerDTO> getFilteredManufacturers(
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) @Min(value = 0, message = "Current page must be higher than 0") int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order) {
        return manufacturerService.getFilteredManufacturers(name, pageSize, order, currentPage);
    }
}