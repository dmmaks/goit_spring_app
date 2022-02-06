package com.edu.goit.controller;

import com.edu.goit.dto.IngredientDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.model.Ingredient;
import com.edu.goit.model.form.SearchIngredientModel;
import com.edu.goit.service.interfaces.IngredientCategoryService;
import com.edu.goit.service.interfaces.IngredientService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
@RequestMapping("/api/ingredient")
@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientController(IngredientService ingredientService, IngredientCategoryService ingredientCategoryService){
        this.ingredientService = ingredientService;
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    public PaginationDTO<Ingredient> getAllIngredients(@Valid @RequestBody SearchIngredientModel searchIngredientModel) {
        return ingredientService.getAllIngredients(searchIngredientModel);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void updateIngredient(@Valid @RequestBody IngredientDTO ingredientDto,
                                 @NotNull(message = "ID is mandatory")
                                 @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id) {
        ingredientService.updateIngredient(ingredientDto, id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Ingredient not found")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/{id}")
    public void updateStatusIngredient(@NotNull(message = "ID is mandatory") @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id,
                                       @NotNull(message = "Status is mandatory") @RequestParam boolean status) {
        ingredientService.updateStatus(id, status);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Ingredient not found")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public Ingredient getIngredient(@NotNull(message = "ID is mandatory") @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/create")
    public void createIngredient(@Valid @RequestBody IngredientDTO ingredientDTO) {
        ingredientService.createIngredient(ingredientDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/category")
    public Collection<String> getAllCategory(){
        return ingredientCategoryService.getAllCategory();
    }
}
