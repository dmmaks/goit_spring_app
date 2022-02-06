package com.edu.goit.service.interfaces;

import com.edu.goit.dto.IngredientDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.model.Ingredient;
import com.edu.goit.model.form.SearchIngredientModel;

public interface IngredientService {
    void createIngredient(IngredientDTO ingredientDto);
    Ingredient getIngredientById(Long id);
    PaginationDTO<Ingredient> getAllIngredients(SearchIngredientModel searchIngredientModel);
    void updateIngredient(IngredientDTO ingredientDto, Long id);
    void updateStatus(Long id, boolean status);
}
